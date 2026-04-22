package com.agrigov.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {
	//List of fields sending as a response.
	private Long auditId;
	private Long userId;
	private String scope;
	private String findings;
	private String status;
	private LocalDate date;
}
