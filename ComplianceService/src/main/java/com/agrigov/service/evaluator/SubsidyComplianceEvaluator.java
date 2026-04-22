package com.agrigov.service.evaluator;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.agrigov.client.SchemeClient;
import com.agrigov.client.SubsidyClient;
import com.agrigov.dto.external.SchemeComplianceDTO;
import com.agrigov.dto.external.SubsidyComplianceDTO;
import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.SchemeStatus;
import com.agrigov.enums.SubsidyStatus;


@Service
public class SubsidyComplianceEvaluator implements ComplianceEvaluator {

    private final SubsidyClient subsidyClient;
    private final SchemeClient schemeClient;

    public SubsidyComplianceEvaluator(
            SubsidyClient subsidyClient,
            SchemeClient schemeClient) {
        this.subsidyClient = subsidyClient;
        this.schemeClient = schemeClient;
    }

    @Override
    public ComplianceResult evaluate(Long subsidyId) {

        SubsidyComplianceDTO subsidy =
                subsidyClient.getSubsidy(subsidyId);

        if (subsidy.getStatus() != SubsidyStatus.APPROVED) {
            return ComplianceResult.NON_COMPLIANT;
        }

        SchemeComplianceDTO scheme =
                schemeClient.getComplianceData(subsidy.getSchemeId());

        if (scheme.getStatus() != SchemeStatus.ACTIVE) {
            return ComplianceResult.NON_COMPLIANT;
        }

        if (scheme.getEndDate().isBefore(LocalDate.now())) {
            return ComplianceResult.NON_COMPLIANT;
        }

        if (subsidy.getAmount() > scheme.getBudget()) {
            return ComplianceResult.NON_COMPLIANT;
        }

        return ComplianceResult.COMPLIANT;
    }
}
//@Service
//public class SubsidyComplianceEvaluator implements ComplianceEvaluator {
//
//    private final SubsidyClient subsidyClient;
//    private final SchemeClient schemeClient;
//
//    public SubsidyComplianceEvaluator(
//            SubsidyClient subsidyClient,
//            SchemeClient schemeClient) {
//
//        this.subsidyClient = subsidyClient;
//        this.schemeClient = schemeClient;
//    }
//
//    @Override
//    public ComplianceResult evaluate(Long subsidyId) {
//
//        SubsidyComplianceDTO subsidy =
//                subsidyClient.getSubsidy(subsidyId);
//
//        if (!"APPROVED".equalsIgnoreCase(subsidy.getStatus())) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        SchemeComplianceDTO scheme =
//                schemeClient.getComplianceData(subsidy.getSchemeId());
//
//        if (!"ACTIVE".equalsIgnoreCase(scheme.getStatus())) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        if (subsidy.getAmount().compareTo(scheme.getBudget()) > 0) {
//            return ComplianceResult.NON_COMPLIANT;
//        }
//
//        return ComplianceResult.COMPLIANT;
//    }
//}



