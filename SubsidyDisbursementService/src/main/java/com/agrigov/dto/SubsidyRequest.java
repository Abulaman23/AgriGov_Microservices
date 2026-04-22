package com.agrigov.dto;

import com.agrigov.enums.SubsidyStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubsidyRequest {
	@NotNull(message = "schemeId is required")
	private Long schemeId;

	@NotNull(message = "farmerId is required")
	private Long farmerId;

	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "amount must be > 0")
	private Double amount;

	@NotNull(message = "status is required")
	private SubsidyStatus status;

}