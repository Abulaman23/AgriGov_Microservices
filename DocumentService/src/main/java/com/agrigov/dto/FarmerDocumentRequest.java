package com.agrigov.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FarmerDocumentRequest {
	private Long farmerId;
	private String docType;
	private String fileUri;
	private LocalDate uploadedDate;
	//private String verificationStatus;
}