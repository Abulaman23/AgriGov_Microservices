package com.agrigov.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.agrigov.model.AuditLog;
import com.agrigov.service.AuditLogService;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditLogService auditLogService;

    public AuditController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/{id}")
    public AuditLog getAuditById(@PathVariable Long id) {
        return auditLogService.getById(id);
    }

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAll();
    }
}
