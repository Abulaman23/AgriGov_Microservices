package com.agrigov.service.evaluator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.agrigov.client.ProjectClient;
import com.agrigov.dto.external.ProjectComplianceDTO;
import com.agrigov.enums.ComplianceResult;

@Service
public class ProjectComplianceEvaluator implements ComplianceEvaluator {

    private final ProjectClient projectClient;

    public ProjectComplianceEvaluator(ProjectClient projectClient) {
        this.projectClient = projectClient;
    }

    @Override
    public ComplianceResult evaluate(Long projectId) {

        ProjectComplianceDTO project =
                projectClient.getComplianceData(projectId);

        if (!"IN_PROGRESS".equalsIgnoreCase(project.getStatus())) {
            return ComplianceResult.NON_COMPLIANT;
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(project.getStartDate()) ||
            today.isAfter(project.getEndDate())) {
            return ComplianceResult.NON_COMPLIANT;
        }

        if (project.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            return ComplianceResult.NON_COMPLIANT;
        }

        return ComplianceResult.COMPLIANT;
    }
}
