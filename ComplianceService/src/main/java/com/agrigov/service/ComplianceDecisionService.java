package com.agrigov.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;
import com.agrigov.service.evaluator.ComplianceEvaluator;
import com.agrigov.service.evaluator.ProjectComplianceEvaluator;
import com.agrigov.service.evaluator.SchemeComplianceEvaluator;
import com.agrigov.service.evaluator.SubsidyComplianceEvaluator;

@Service
public class ComplianceDecisionService {

    private final Map<ComplianceType, ComplianceEvaluator> evaluators;

    public ComplianceDecisionService(
            ProjectComplianceEvaluator projectEvaluator,
            SchemeComplianceEvaluator schemeEvaluator,
            SubsidyComplianceEvaluator subsidyEvaluator) {

        this.evaluators = Map.of(
            ComplianceType.PROJECT, projectEvaluator,
            ComplianceType.SCHEME, schemeEvaluator,
            ComplianceType.SUBSIDY, subsidyEvaluator
        );
    }

    public ComplianceResult decide(ComplianceType type, Long entityId) {

        ComplianceEvaluator evaluator = evaluators.get(type);

        if (evaluator == null) {
            throw new IllegalArgumentException(
                "Unsupported compliance type: " + type);
        }

        return evaluator.evaluate(entityId);
    }
}

