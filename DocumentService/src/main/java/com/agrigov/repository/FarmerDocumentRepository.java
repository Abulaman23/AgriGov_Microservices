package com.agrigov.repository;

import com.agrigov.model.FarmerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FarmerDocumentRepository extends JpaRepository<FarmerDocument, Long> {
    List<FarmerDocument> findByFarmerId(Long farmerId);
    List<FarmerDocument> findByVerificationStatus(String status);
}