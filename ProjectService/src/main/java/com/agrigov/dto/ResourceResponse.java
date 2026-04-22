package com.agrigov.dto;

import java.math.BigDecimal;

import com.agrigov.enums.ResourceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceResponse {

	// Resource fields
	private Long resourceId;
	private ResourceType type;
	private BigDecimal quantity;
	private String status;

	// Project summary (not the full RuralProject entity)
	private Long projectId;
	private String projectTitle;
}