package com.agrigov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SchemeRequestDTO {

	@JsonProperty("schemeID")
	private Long schemeId;

	@JsonProperty("farmerID")
	private Long farmerId;

}
