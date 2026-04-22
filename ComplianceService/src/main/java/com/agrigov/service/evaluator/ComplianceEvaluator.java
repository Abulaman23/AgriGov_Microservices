package com.agrigov.service.evaluator;

import com.agrigov.enums.ComplianceResult;

public interface ComplianceEvaluator {
    ComplianceResult evaluate(Long entityId);
}

