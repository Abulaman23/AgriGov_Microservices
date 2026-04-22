package com.agrigov.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FarmerDocumentResponse {
    private Long documentId;
    private Long farmerId;
    private String docType;
    private String fileUri;
    private LocalDate uploadedDate;
    private String verificationStatus;
}