package com.agrigov.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FarmerDocuments")
@Data
public class FarmerDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false) // Farmer ID must exist
    private Long farmerId; 

    @Column(nullable = false, length = 50) // e.g., "AADHAR", "LAND_RECORD"
    private String docType;

    @Column(nullable = false, length = 1000) // File paths can be long
    private String fileUri; 

    private LocalDate uploadedDate;

    @Column(length = 20) // e.g., "PENDING", "APPROVED"
    private String verificationStatus;
}