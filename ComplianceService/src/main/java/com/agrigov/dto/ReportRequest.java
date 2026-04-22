package com.agrigov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
	//List of fields getting in request.
	private Long reportId;
	private String scope;
	private String metrics;
	private Long userId;
	private Long auditId;
}
