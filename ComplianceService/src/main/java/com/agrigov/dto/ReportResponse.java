package com.agrigov.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
	//List of fields sending as a response.
	private Long reportId;
	private String scope;
	private String metrics;
	private LocalDate date;
	private Long userId;
	private Long auditId;
}
