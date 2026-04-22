package com.agrigov.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
public class MilestoneRequest {

	@NotNull(message = "projectId is required")
	private Long projectId;

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title must be at most 100 characters")
	private String title;

	@NotNull(message = "Date is required")
	private LocalDate date;

	@NotNull(message = "Status is required")

	@Pattern(

			regexp = "PLANNED|ALLOCATED|UTILIZED|ON_HOLD|CANCELLED",

			message = "Status must be one of: PLANNED, IN_PROGRESS, COMPLETED, DELAYED, CANCELLED"

	)

	@Column(name = "status", nullable = false, length = 20)

	private String status;
}