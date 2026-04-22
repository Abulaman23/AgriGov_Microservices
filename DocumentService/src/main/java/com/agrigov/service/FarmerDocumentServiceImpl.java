package com.agrigov.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agrigov.dto.FarmerDocumentResponse;
import com.agrigov.exception.ResourceNotFoundException;
import com.agrigov.model.FarmerDocument;
import com.agrigov.repository.FarmerDocumentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class FarmerDocumentServiceImpl implements FarmerDocumentService {

    private final FarmerDocumentRepository documentRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public FarmerDocumentResponse uploadDocument(Long farmerId, String docType, MultipartFile file) {
        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get(uploadDir));

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            
            // Save physical file
            Files.write(filePath, file.getBytes());

            // Save metadata to database
            FarmerDocument document = new FarmerDocument();
            document.setFarmerId(farmerId);
            document.setDocType(docType);
            document.setFileUri(fileName);
            document.setUploadedDate(LocalDate.now());
            document.setVerificationStatus("Pending");

            FarmerDocument saved = documentRepository.save(document);
            return toResponse(saved);

        } catch (IOException e) {
            log.error("Error saving file: {}", e.getMessage());
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    /**
     * Implementation for metadata-only updates (Requirement from Interface)
     */
    @Override
    public FarmerDocumentResponse updateDocument(Long documentId, String docType, String status) {
        FarmerDocument existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        if (docType != null) {
            existing.setDocType(docType);
        }
        if (status != null) {
            existing.setVerificationStatus(status);
        }

        FarmerDocument updated = documentRepository.save(existing);
        return toResponse(updated);
    }

    /**
     * Implementation for file replacement updates
     */
    @Override
    public FarmerDocumentResponse updateDocument(Long documentId, String docType, MultipartFile file) {
        FarmerDocument existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        try {
            if (file != null && !file.isEmpty()) {
                // Delete the old file from disk
                Path oldFilePath = Paths.get(uploadDir, existing.getFileUri());
                Files.deleteIfExists(oldFilePath);

                // Save the new file
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path newFilePath = Paths.get(uploadDir, fileName);
                Files.write(newFilePath, file.getBytes());

                existing.setFileUri(fileName);
                existing.setUploadedDate(LocalDate.now());
            }

            if (docType != null) {
                existing.setDocType(docType);
            }
            
            existing.setVerificationStatus("Pending"); // Reset status on file change

            return toResponse(documentRepository.save(existing));

        } catch (IOException e) {
            log.error("Error updating file: {}", e.getMessage());
            throw new RuntimeException("File update failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteDocument(Long documentId) {
        FarmerDocument doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        try {
            // Remove physical file
            Files.deleteIfExists(Paths.get(uploadDir, doc.getFileUri()));
        } catch (IOException e) {
            log.warn("Failed to delete physical file: {}", doc.getFileUri());
        }

        documentRepository.deleteById(documentId);
    }

    @Override
    public FarmerDocumentResponse getDocumentById(Long documentId) {
        return documentRepository.findById(documentId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
    }

    @Override
    public List<FarmerDocumentResponse> getDocumentsByFarmer(Long farmerId) {
        return documentRepository.findByFarmerId(farmerId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<FarmerDocumentResponse> getDocumentsByStatus(String status) {
        return documentRepository.findByVerificationStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Manual Mapper helper
     */
    private FarmerDocumentResponse toResponse(FarmerDocument doc) {
        FarmerDocumentResponse response = new FarmerDocumentResponse();
        response.setDocumentId(doc.getDocumentId());
        response.setFarmerId(doc.getFarmerId());
        response.setDocType(doc.getDocType());
        response.setFileUri(doc.getFileUri());
        response.setUploadedDate(doc.getUploadedDate());
        response.setVerificationStatus(doc.getVerificationStatus());
        return response;
    }
}