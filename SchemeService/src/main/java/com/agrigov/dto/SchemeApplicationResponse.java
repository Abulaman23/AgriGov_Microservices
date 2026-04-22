package com.agrigov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.agrigov.enums.SchemeStatus;

/**
 * Response DTO for SchemeApplication.
 * Mirrors ERD core fields and includes the primary key.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeApplicationResponse {
	//List of fields sending as a response.
    private Long applicationID;
    private Long farmerID;
    private Long schemeID;
    private LocalDate submittedDate;
    private SchemeStatus status;
}