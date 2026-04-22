package com.agrigov.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.agrigov.dto.FarmerDocumentResponse;

public interface FarmerDocumentService {
    
    /**
     * Upload a new document for a specific farmer.
     * The farmerId is stored as a Long in the documentdb.
     */
    FarmerDocumentResponse uploadDocument(Long farmerId, String docType, MultipartFile file);

    /**
     * Update existing document metadata (like docType or verificationStatus).
     * Note: In microservices, we usually avoid passing the full Request DTO 
     * if we only want to update specific fields.
     */
    FarmerDocumentResponse updateDocument(Long documentId, String docType, String status);

    /**
     * Update document by replacing the physical file and updating the type.
     */
    FarmerDocumentResponse updateDocument(Long documentId, String docType, MultipartFile file);

    /**
     * Delete document (Deletes entry from database AND file from local folder).
     */
    void deleteDocument(Long documentId);

    /**
     * Fetch metadata for a single document.
     */
    FarmerDocumentResponse getDocumentById(Long documentId);

    /**
     * This is the method used by FarmerService via OpenFeign.
     */
    List<FarmerDocumentResponse> getDocumentsByFarmer(Long farmerId);

    /**
     * Fetch documents for administrative approval/verification.
     */
    List<FarmerDocumentResponse> getDocumentsByStatus(String status);
}