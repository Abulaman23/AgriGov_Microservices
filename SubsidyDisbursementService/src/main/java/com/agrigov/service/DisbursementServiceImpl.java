package com.agrigov.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.client.DisbursementNotificationClient;
import com.agrigov.dto.DisbursementRequest;
import com.agrigov.dto.DisbursementResponse;
import com.agrigov.dto.NotificationRequest;
import com.agrigov.enums.NotificationCategory;
import com.agrigov.exception.ConflictException;
import com.agrigov.exception.ResourceNotFoundException;
import com.agrigov.model.Disbursement;
import com.agrigov.model.Subsidy;
import com.agrigov.repository.DisbursementRepository;
import com.agrigov.repository.SubsidyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisbursementServiceImpl implements DisbursementService {

	private final DisbursementRepository disbursementRepository;
	private final SubsidyRepository subsidyRepository;
	// private final UserRepository userRepository;
	private final DisbursementNotificationClient disbursementnotificationClient;

	// Creates a new Disbursement for an approved Subsidy.
	@Override
	@Transactional
	public DisbursementResponse create(DisbursementRequest request) {
		// Prevent duplicate disbursement for the same subsidy
		boolean alreadyDisbursed = disbursementRepository.findAll().stream()
				.anyMatch(d -> d.getSubsidy().getSubsidyId().equals(request.getSubsidyId()));

		if (alreadyDisbursed) {
			log.warn("Disbursement already done for subsidyId={}", request.getSubsidyId());
			throw new ConflictException("Disbursement already completed for this subsidy");
		}

		Subsidy subsidy = subsidyRepository.findById(request.getSubsidyId()).orElseThrow(() -> {
			log.warn("Subsidy not found: {}", request.getSubsidyId());
			return new ResourceNotFoundException("Subsidy not found: " + request.getSubsidyId());
		});

		// Disbursement is allowed only if the subsidy is APPROVED
//		if (!"APPROVED".equalsIgnoreCase(subsidy.getStatus())) {
//			log.warn("Disbursement denied. Subsidy id={} status={}", subsidy.getSubsidyId(), subsidy.getStatus());
//			throw new IllegalStateException("Disbursement allowed only for APPROVED subsidies");
//		}

//		User officer = userRepository.findById(request.getOfficerId()).orElseThrow(() -> {
//			log.warn("Officer not found: {}", request.getOfficerId());
//			return new ResourceNotFoundException("Officer not found: " + request.getOfficerId());
//		});

		Disbursement entity = new Disbursement();
		entity.setSubsidy(subsidy);
		// entity.setOfficer(officer);
		entity.setStatus("DISBURSED");

		entity = disbursementRepository.save(entity);

		log.info("Disbursement created successfully id={}", entity.getDisbursementId());

		// --- Notification Logic Added Here ---
		triggerDisbursementNotification(entity);

		return toResponse(entity);
	}

	// Fetches a Disbursement by its ID.
	@Override
	public DisbursementResponse getById(Long id) {
		log.info("Fetching Disbursement id={}", id);
		return toResponse(findEntity(id));
	}

	// Retrieves all Disbursement records
	@Override
	public List<DisbursementResponse> getAll() {
		log.info("Fetching all Disbursements");
		return disbursementRepository.findAll().stream().map(this::toResponse).toList();
	}

	// Updates an existing Disbursement.
	@Override
	@Transactional
	public DisbursementResponse update(Long id, DisbursementRequest request) {

		log.info("Updating Disbursement id={}", id);

		Disbursement entity = findEntity(id);

		Subsidy subsidy = subsidyRepository.findById(request.getSubsidyId()).orElseThrow(() -> {
			log.warn("Subsidy not found: {}", request.getSubsidyId());
			return new ResourceNotFoundException("Subsidy not found: " + request.getSubsidyId());
		});

//		User officer = userRepository.findById(request.getOfficerId()).orElseThrow(() -> {
//			log.warn("Officer not found: {}", request.getOfficerId());
//			return new ResourceNotFoundException("Officer not found: " + request.getOfficerId());
//		});

		entity.setSubsidy(subsidy);
		// entity.setOfficer(officer);

		log.info("Disbursement updated successfully id={}", id);
		return toResponse(entity);
	}

	// Deletes a Disbursement by its ID.
	@Override
	public void delete(Long id) {

		log.info("Deleting Disbursement id={}", id);

		if (!disbursementRepository.existsById(id)) {
			log.warn("Disbursement not found id={}", id);
			throw new ResourceNotFoundException("Disbursement not found: " + id);
		}

		disbursementRepository.deleteById(id);
		log.info("Disbursement deleted successfully id={}", id);
	}

	// Helper method to trigger the Feign Client call
	private void triggerDisbursementNotification(Disbursement entity) {
		try {
			NotificationRequest note = new NotificationRequest();

			// Default values matching your SchemeService requirements
			note.setUserId(1L);
			note.setEmail("gs180271@gmail.com");

			note.setEntityId(entity.getDisbursementId());
			note.setMessage("Success! Your subsidy disbursement for ID: " + entity.getSubsidy().getSubsidyId()
					+ " has been processed.");
			note.setCategory(NotificationCategory.SCHEME);

			disbursementnotificationClient.createNotification(note);
			log.info("Notification sent for Disbursement ID: {}", entity.getDisbursementId());
		} catch (Exception e) {
			log.warn("Notification service unreachable for disbursement ID {}. Error: {}", entity.getDisbursementId(),
					e.getMessage());
		}
	}

	// Helper method to fetch Disbursement entity by ID.
	private Disbursement findEntity(Long id) {
		return disbursementRepository.findById(id).orElseThrow(() -> {
			log.warn("Disbursement not found id={}", id);
			return new ResourceNotFoundException("Disbursement not found: " + id);
		});
	}

	// Converts Disbursement entity into DisbursementResponse DTO.
	private DisbursementResponse toResponse(Disbursement d) {
		DisbursementResponse res = new DisbursementResponse();
		res.setDisbursementId(d.getDisbursementId());
		res.setSubsidyId(d.getSubsidy().getSubsidyId());
		// res.setOfficerId(d.getOfficer().getUserId());
		res.setDate(d.getDate());
		res.setStatus(d.getStatus());
		return res;
	}
}
/*
 * package com.agrigov.service;
 * 
 * import java.util.List;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.agrigov.dto.DisbursementRequest; import
 * com.agrigov.dto.DisbursementResponse; import
 * com.agrigov.exception.ConflictException; import
 * com.agrigov.exception.ResourceNotFoundException; import
 * com.agrigov.model.Disbursement; import com.agrigov.model.Subsidy; import
 * com.agrigov.repository.DisbursementRepository; import
 * com.agrigov.repository.SubsidyRepository;
 * 
 * import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor
 * 
 * @Slf4j public class DisbursementServiceImpl implements DisbursementService {
 * 
 * private final DisbursementRepository disbursementRepository; private final
 * SubsidyRepository subsidyRepository; //private final UserRepository
 * userRepository;
 * 
 * // Creates a new Disbursement for an approved Subsidy.
 * 
 * @Override public DisbursementResponse create(DisbursementRequest request) {
 * // Prevent duplicate disbursement for the same subsidy boolean
 * alreadyDisbursed = disbursementRepository.findAll().stream() .anyMatch(d ->
 * d.getSubsidy().getSubsidyId().equals(request.getSubsidyId()));
 * 
 * if (alreadyDisbursed) {
 * log.warn("Disbursement already done for subsidyId={}",
 * request.getSubsidyId()); throw new
 * ConflictException("Disbursement already completed for this subsidy"); }
 * 
 * Subsidy subsidy =
 * subsidyRepository.findById(request.getSubsidyId()).orElseThrow(() -> {
 * log.warn("Subsidy not found: {}", request.getSubsidyId()); return new
 * ResourceNotFoundException("Subsidy not found: " + request.getSubsidyId());
 * });
 * 
 * // Disbursement is allowed only if the subsidy is APPROVED // if
 * (!"APPROVED".equalsIgnoreCase(subsidy.getStatus())) { //
 * log.warn("Disbursement denied. Subsidy id={} status={}",
 * subsidy.getSubsidyId(), subsidy.getStatus()); // throw new
 * IllegalStateException("Disbursement allowed only for APPROVED subsidies"); //
 * }
 * 
 * // User officer =
 * userRepository.findById(request.getOfficerId()).orElseThrow(() -> { //
 * log.warn("Officer not found: {}", request.getOfficerId()); // return new
 * ResourceNotFoundException("Officer not found: " + request.getOfficerId()); //
 * });
 * 
 * Disbursement entity = new Disbursement(); entity.setSubsidy(subsidy);
 * //entity.setOfficer(officer); entity.setStatus("DISBURSED");
 * 
 * entity = disbursementRepository.save(entity);
 * 
 * log.info("Disbursement created successfully id={}",
 * entity.getDisbursementId());
 * 
 * return toResponse(entity); }
 * 
 * // Fetches a Disbursement by its ID.
 * 
 * @Override public DisbursementResponse getById(Long id) {
 * log.info("Fetching Disbursement id={}", id); return
 * toResponse(findEntity(id)); }
 * 
 * // Retrieves all Disbursement records
 * 
 * @Override public List<DisbursementResponse> getAll() {
 * log.info("Fetching all Disbursements"); return
 * disbursementRepository.findAll().stream().map(this::toResponse).toList(); }
 * 
 * // Updates an existing Disbursement.
 * 
 * @Override public DisbursementResponse update(Long id, DisbursementRequest
 * request) {
 * 
 * log.info("Updating Disbursement id={}", id);
 * 
 * Disbursement entity = findEntity(id);
 * 
 * Subsidy subsidy =
 * subsidyRepository.findById(request.getSubsidyId()).orElseThrow(() -> {
 * log.warn("Subsidy not found: {}", request.getSubsidyId()); return new
 * ResourceNotFoundException("Subsidy not found: " + request.getSubsidyId());
 * });
 * 
 * // User officer =
 * userRepository.findById(request.getOfficerId()).orElseThrow(() -> { //
 * log.warn("Officer not found: {}", request.getOfficerId()); // return new
 * ResourceNotFoundException("Officer not found: " + request.getOfficerId()); //
 * });
 * 
 * entity.setSubsidy(subsidy); //entity.setOfficer(officer);
 * 
 * log.info("Disbursement updated successfully id={}", id); return
 * toResponse(entity); }
 * 
 * // Deletes a Disbursement by its ID.
 * 
 * @Override public void delete(Long id) {
 * 
 * log.info("Deleting Disbursement id={}", id);
 * 
 * if (!disbursementRepository.existsById(id)) {
 * log.warn("Disbursement not found id={}", id); throw new
 * ResourceNotFoundException("Disbursement not found: " + id); }
 * 
 * disbursementRepository.deleteById(id);
 * log.info("Disbursement deleted successfully id={}", id); }
 * 
 * // Helper method to fetch Disbursement entity by ID. private Disbursement
 * findEntity(Long id) { return
 * disbursementRepository.findById(id).orElseThrow(() -> {
 * log.warn("Disbursement not found id={}", id); return new
 * ResourceNotFoundException("Disbursement not found: " + id); }); }
 * 
 * // Converts Disbursement entity into DisbursementResponse DTO. private
 * DisbursementResponse toResponse(Disbursement d) { DisbursementResponse res =
 * new DisbursementResponse(); res.setDisbursementId(d.getDisbursementId());
 * res.setSubsidyId(d.getSubsidy().getSubsidyId()); //
 * res.setOfficerId(d.getOfficer().getUserId()); res.setDate(d.getDate());
 * res.setStatus(d.getStatus()); return res; } }
 * 
 */