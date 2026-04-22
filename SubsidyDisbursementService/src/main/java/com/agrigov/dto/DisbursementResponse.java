package com.agrigov.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DisbursementResponse {
    private Long disbursementId;
    private Long subsidyId;
    private Long officerId;
    private LocalDate date;
    private String status;
}