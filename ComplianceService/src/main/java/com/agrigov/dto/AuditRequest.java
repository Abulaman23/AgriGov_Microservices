package com.agrigov.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRequest {
	//List of fields getting in request.
	private Long auditId;
	private Long userId;
	private String scope;
	private String findings;
	private String status;
}
