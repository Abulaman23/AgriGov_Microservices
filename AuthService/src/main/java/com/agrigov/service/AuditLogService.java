package com.agrigov.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrigov.model.AuditLog;
import com.agrigov.repository.AuditLogRepository;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void write(AuditLog log) {
        auditLogRepository.save(log);
    }

    public AuditLog getById(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAll();
    }
}
