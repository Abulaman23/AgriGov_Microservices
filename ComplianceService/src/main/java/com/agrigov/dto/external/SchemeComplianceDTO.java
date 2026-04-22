package com.agrigov.dto.external;

import java.time.LocalDate;

import com.agrigov.enums.SchemeStatus;

import lombok.Data;

@Data
public class SchemeComplianceDTO {
    private Long schemeId;
    private SchemeStatus status;
    private Double budget;
    private LocalDate endDate;
}

