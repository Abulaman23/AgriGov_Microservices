package com.agrigov.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class FarmerResponse {

	private Long farmerId;
	private String name;
	private LocalDate dob;
	private String gender;
	private String address;
	private String contactInfo;
	private String landDetails;
	private String status;

	// Include documents
	private List<FarmerDocumentResponse> documents;

	// Custom getter to control JSON output if document is empty
	public Object getDocuments() {
		if (documents == null || documents.isEmpty()) {
			return "Document not uploaded";
		}
		return documents;
	}

}
