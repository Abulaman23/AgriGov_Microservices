package com.agrigov.dto;

import java.time.LocalDate;

import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecordResponse {
    private Long complianceId;
    private Long entityID;
    private ComplianceType type;
    private ComplianceResult result;
    private String notes;
    private LocalDate date;
}
