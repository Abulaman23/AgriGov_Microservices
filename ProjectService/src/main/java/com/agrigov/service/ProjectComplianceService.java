package com.agrigov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.client.ComplianceClient;
import com.agrigov.exception.RuralProjectNotFound;
import com.agrigov.model.RuralProject;
import com.agrigov.repo.RuralProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectComplianceService {

    private final ComplianceClient complianceClient;
    private final RuralProjectRepository repository;

    @Transactional
    public void syncCompliance(Long projectId) {

        RuralProject project = repository.findById(projectId)
                .orElseThrow(() ->
                        new RuralProjectNotFound(
                                "RuralProject not found: " + projectId));

        String result = complianceClient.getComplianceResult(
                projectId,
                "PROJECT"
        );

        project.setResult(result);      // ✅ already present in entity
        project.setComplianceId(projectId);

        repository.save(project);
    }
}