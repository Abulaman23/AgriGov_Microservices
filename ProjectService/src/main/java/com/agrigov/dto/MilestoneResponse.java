package com.agrigov.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneResponse {

	private Long milestoneId;
	private String title;
	private LocalDate date;
	private String status;

	// Project summary (not the full RuralProject entity)
	private Long projectId;
	private String projectTitle;
}