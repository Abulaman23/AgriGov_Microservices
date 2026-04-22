package com.agrigov.dto;

import com.agrigov.enums.ComplianceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceRecordRequest {

    private Long entityID;
    private ComplianceType type;
    private String notes;
}