package com.agrigov.dto;

import java.time.LocalDate;

import com.agrigov.enums.SubsidyStatus;

import lombok.Data;

@Data
public class SubsidyResponse {
	private Long subsidyId;
	private Long schemeId;
	private Long farmerId;
	private Double amount;
	private LocalDate date;
	private SubsidyStatus status;
}