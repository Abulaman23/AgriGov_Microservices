package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.ComplianceRecordRequest;
import com.agrigov.dto.ComplianceRecordResponse;
import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;

public interface ComplianceRecordService {

    ComplianceRecordResponse create(ComplianceRecordRequest request);

    ComplianceRecordResponse get(Long complianceId);

    List<ComplianceRecordResponse> getAll();

    ComplianceRecordResponse delete(Long complianceId);

    // ✅ Enum-based business method
    ComplianceResult getComplianceResult(Long entityId, ComplianceType type);
}
