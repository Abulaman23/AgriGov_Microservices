package com.agrigov.service.evaluator;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.agrigov.client.SchemeClient;
import com.agrigov.dto.external.SchemeComplianceDTO;
import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.SchemeStatus;


@Service
public class SchemeComplianceEvaluator implements ComplianceEvaluator {

    private final SchemeClient schemeClient;

    public SchemeComplianceEvaluator(SchemeClient schemeClient) {
        this.schemeClient = schemeClient;
    }

    @Override
    public ComplianceResult evaluate(Long schemeId) {

        SchemeComplianceDTO scheme =
                schemeClient.getComplianceData(schemeId);

        if (scheme.getStatus() != SchemeStatus.ACTIVE) {
            return ComplianceResult.NON_COMPLIANT;
        }

        if (scheme.getEndDate().isBefore(LocalDate.now())) {
            return ComplianceResult.NON_COMPLIANT;
        }

        if (scheme.getBudget() <= 0) {
            return ComplianceResult.NON_COMPLIANT;
        }

        return ComplianceResult.COMPLIANT;
    }
}
//@Service
//public class SchemeComplianceEvaluator implements ComplianceEvaluator {
//
//    private final SchemeClient schemeClient;
//
//    public SchemeComplianceEvaluator(SchemeClient schemeClient) {
//        this.schemeClient = schemeClient;
//    }
//
//    @Override
//    public ComplianceResult evaluate(Long schemeId) {
//
//        SchemeComplianceDTO scheme =
//                schemeClient.getComplianceData(schemeId);
//
//        if (!"ACTIVE".equalsIgnoreCase(scheme.getStatus())) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        if (scheme.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        if (LocalDate.now().isAfter(scheme.getEndDate())) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        return ComplianceResult.COMPLIANT;
//    }
//}
