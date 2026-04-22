package com.agrigov.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuralProjectRequest {

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title must be at most 100 characters")
	private String title;

	@Size(max = 2000, message = "Description must be at most 2000 characters")
	private String description;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;

	// Can be null for ongoing projects
	private LocalDate endDate;

	@NotNull(message = "Budget is required")
	@DecimalMin(value = "0.00", inclusive = true, message = "Budget cannot be negative")
	private BigDecimal budget;

	@NotBlank(message = "Status is required")
	@Pattern(regexp = "PLANNED|IN_PROGRESS|COMPLETED|ON_HOLD|CANCELLED", message = "Status must be one of: PLANNED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED")
	private String status;

	private Set<Long> resourceIds; 
	private Set<Long> milestoneIds; 
	
	
	
	

}