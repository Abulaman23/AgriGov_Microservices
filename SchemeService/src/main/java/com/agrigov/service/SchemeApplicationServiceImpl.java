package com.agrigov.service;

import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.agrigov.client.FarmerClient;
import com.agrigov.client.SchemeNotificationClient;
import com.agrigov.dto.FarmerResponse;
import com.agrigov.dto.NotificationRequest;
import com.agrigov.dto.SchemeApplicationRequest;
import com.agrigov.dto.SchemeApplicationResponse;
import com.agrigov.enums.NotificationCategory;
import com.agrigov.enums.SchemeStatus;
import com.agrigov.exception.SchemeApplicationNotFoundException;
import com.agrigov.model.PolicyScheme;
import com.agrigov.model.SchemeApplication;
import com.agrigov.repository.PolicySchemeRepository;
import com.agrigov.repository.SchemeApplicationRepository;
 
import lombok.extern.slf4j.Slf4j;
 
@Service
@Transactional
@Slf4j
public class SchemeApplicationServiceImpl implements SchemeApplicationService {
 
    private final SchemeApplicationRepository schemeApplicationRepository;
    private final PolicySchemeRepository policySchemeRepository;
    private final SchemeNotificationClient schemenotificationClient;
    private final FarmerClient farmerClient;
 
    public SchemeApplicationServiceImpl(
            SchemeApplicationRepository schemeApplicationRepository,
            PolicySchemeRepository policySchemeRepository,
            SchemeNotificationClient schemenotificationClient,
            FarmerClient farmerClient) {
 
        this.schemeApplicationRepository = schemeApplicationRepository;
        this.policySchemeRepository = policySchemeRepository;
        this.schemenotificationClient = schemenotificationClient;
        this.farmerClient = farmerClient;
    }
 
    // ---------------- CREATE ----------------
 
    @Override
    public SchemeApplicationResponse create(SchemeApplicationRequest request) {
        log.info("Processing application for Farmer ID: {} and Scheme ID: {}", request.getFarmerID(), request.getSchemeID());
 
        // 1. VALIDATION: Verify Farmer via Feign Client
        validateFarmerEligibility(request.getFarmerID());
 
        // 2. VALIDATION: Verify Policy Scheme via Repository
        PolicyScheme policyScheme = policySchemeRepository.findById(request.getSchemeID())
                .orElseThrow(() -> new SchemeApplicationNotFoundException("PolicyScheme not found for ID: " + request.getSchemeID()));
 
        // 3. MAPPING & SAVING
        SchemeApplication schemeApplication = new SchemeApplication();
        schemeApplication.setPolicyscheme(policyScheme);
        schemeApplication.setFarmerID(request.getFarmerID());
       
        // FIXED: Using Enum instead of String "PENDING"
        schemeApplication.setStatus(SchemeStatus.PENDING);
       
        schemeApplication.setSubmittedDate(request.getSubmittedDate() != null ? request.getSubmittedDate() : java.time.LocalDate.now());
 
        schemeApplication = schemeApplicationRepository.save(schemeApplication);
 
        // 4. ASYNC NOTIFICATION TRIGGER
        triggerNotification(schemeApplication);
 
        return toResponse(schemeApplication);
    }
 
    // ---------------- UPDATE ----------------
 
    @Override
    public SchemeApplicationResponse update(Long applicationID, SchemeApplicationRequest request) {
        SchemeApplication schemeApplication = schemeApplicationRepository.findById(applicationID)
                .orElseThrow(() -> new SchemeApplicationNotFoundException("Scheme application not found for: " + applicationID));
 
        // Update fields if provided
        if (request.getStatus() != null) {
            schemeApplication.setStatus(request.getStatus()); // This works now because request.getStatus() is SchemeStatus enum
        }
       
        // If scheme ID changes, validate the new scheme
        if (request.getSchemeID() != null) {
            PolicyScheme newScheme = policySchemeRepository.findById(request.getSchemeID())
                    .orElseThrow(() -> new SchemeApplicationNotFoundException("PolicyScheme not found for ID: " + request.getSchemeID()));
            schemeApplication.setPolicyscheme(newScheme);
        }
 
        schemeApplication = schemeApplicationRepository.save(schemeApplication);
        return toResponse(schemeApplication);
    }
 
    // ---------------- READ OPERATIONS ----------------
 
    @Override
    @Transactional(readOnly = true)
    public List<SchemeApplicationResponse> getAppliedBySchemeId(Long schemeID) {
        if (!policySchemeRepository.existsById(schemeID)) {
            throw new SchemeApplicationNotFoundException("PolicyScheme not found for: " + schemeID);
        }
        return schemeApplicationRepository.findByPolicyscheme_SchemeID(schemeID).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<SchemeApplicationResponse> getAllApplications() {
        return schemeApplicationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public SchemeApplicationResponse getById(Long applicationID) {
        SchemeApplication schemeApplication = schemeApplicationRepository.findById(applicationID)
                .orElseThrow(() -> new SchemeApplicationNotFoundException("Scheme application not found for: " + applicationID));
        return toResponse(schemeApplication);
    }
 
    // ---------------- DELETE ----------------
 
    @Override
    public void deleteappl(Long applicationID) {
        if (!schemeApplicationRepository.existsById(applicationID)) {
            throw new SchemeApplicationNotFoundException("Cannot delete: Application not found for ID: " + applicationID);
        }
        schemeApplicationRepository.deleteById(applicationID);
        log.info("Deleted Scheme Application ID: {}", applicationID);
    }
 
    // ---------------- PRIVATE HELPER METHODS ----------------
 
    private void validateFarmerEligibility(Long farmerId) {
        try {
            FarmerResponse farmer = farmerClient.getFarmerById(farmerId);
            if (farmer == null) {
                throw new SchemeApplicationNotFoundException("Farmer verification failed: Farmer does not exist.");
            }
            // Comparing String status from Response DTO
            if (!"ACTIVE".equalsIgnoreCase(farmer.getStatus())) {
                throw new IllegalStateException("Farmer is not eligible to apply. Current status: " + farmer.getStatus());
            }
        } catch (Exception e) {
            log.error("Error verifying farmer ID {}: {}", farmerId, e.getMessage());
            throw new SchemeApplicationNotFoundException("Invalid Farmer: Could not verify identity with FarmerService.");
        }
    }
 
    private void triggerNotification(SchemeApplication entity) {
        try {
            NotificationRequest note = new NotificationRequest();
            note.setUserId(entity.getFarmerID());
            note.setEntityId(entity.getApplicationID());
            note.setEmail("gs180271@gmail.com"); // Replace with actual farmer email lookup if needed
            note.setMessage("Your application for " + entity.getPolicyscheme().getTitle() + " has been successfully submitted.");
            note.setCategory(NotificationCategory.SCHEME);
 
            schemenotificationClient.createNotification(note);
        } catch (Exception e) {
            log.warn("Notification service unreachable for application ID {}. Error: {}", entity.getApplicationID(), e.getMessage());
        }
    }
 
    private SchemeApplicationResponse toResponse(SchemeApplication s) {
        SchemeApplicationResponse r = new SchemeApplicationResponse();
        r.setApplicationID(s.getApplicationID());
        r.setFarmerID(s.getFarmerID());
        r.setSchemeID(s.getPolicyscheme().getSchemeID());
       
        // Converting Enum to String for Response if Response uses String
        r.setStatus(s.getStatus());
       
        r.setSubmittedDate(s.getSubmittedDate());
        return r;
    }
}
 