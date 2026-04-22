package com.agrigov.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrigov.dto.AuditRequest;
import com.agrigov.dto.AuditResponse;

@Service
public interface AuditService {
	AuditResponse create(AuditRequest request);
    AuditResponse update(Long complianceId, AuditRequest request);
    AuditResponse get(Long complianceId);
    List<AuditResponse> getAll();
    AuditResponse delete(Long complianceId);
}
