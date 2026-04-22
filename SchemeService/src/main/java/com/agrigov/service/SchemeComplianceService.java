package com.agrigov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.client.ComplianceClient;
import com.agrigov.exception.SchemeApplicationNotFoundException;
import com.agrigov.model.SchemeApplication;
import com.agrigov.repository.SchemeApplicationRepository;

import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class SchemeComplianceService {
 
    private final ComplianceClient complianceClient;
    private final SchemeApplicationRepository repository;
 
    @Transactional
    public void syncCompliance(Long schemeId) {
 
        SchemeApplication scheme = repository.findById(schemeId)
                .orElseThrow(() ->
                        new SchemeApplicationNotFoundException(
                                "RuralProject not found: " + schemeId));
 
        String result = complianceClient.getComplianceResult(
                schemeId,
                "SCHEME"
        );
 
        scheme.setResult(result);      // ✅ already present in entity
        scheme.setComplianceId(schemeId);
 
        repository.save(scheme);
    }
}
