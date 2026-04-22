package com.agrigov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.enums.ComplianceType;
import com.agrigov.model.ComplianceRecord;


public interface ComplianceRecordRepository
        extends JpaRepository<ComplianceRecord, Long> {

    Optional<ComplianceRecord>
    findTopByEntityIDAndTypeOrderByDateDesc(Long entityID, ComplianceType type);
}

