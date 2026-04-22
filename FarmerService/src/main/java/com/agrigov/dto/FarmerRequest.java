package com.agrigov.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FarmerRequest {

	private String name;
	private LocalDate dob;
	private String gender;
	private String address;
	private String contactInfo;
	private String landDetails;
	//private String status;

}