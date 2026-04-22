package com.agrigov.dto.external;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjectComplianceDTO {
    private Long projectId;
    private String status;
    private BigDecimal budget;
    private LocalDate startDate;
    private LocalDate endDate;
}

