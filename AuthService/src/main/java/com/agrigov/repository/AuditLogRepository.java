package com.agrigov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}