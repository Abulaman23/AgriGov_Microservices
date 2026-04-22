package com.agrigov.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DisbursementRequest {

	@NotNull(message = "subsidyId is required")
	private Long subsidyId;
	
	@NotNull(message = "officerId is required")
	private Long officerId;
}