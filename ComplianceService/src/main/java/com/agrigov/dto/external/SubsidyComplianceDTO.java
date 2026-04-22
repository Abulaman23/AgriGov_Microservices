package com.agrigov.dto.external;

import com.agrigov.enums.SubsidyStatus;

import lombok.Data;

@Data
public class SubsidyComplianceDTO {
	private Long subsidyId;
    private Long schemeId;
    private Double amount;
    private SubsidyStatus status;
}
