package com.agrigov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	private String accessToken;
	private String refreshToken;
	private long expiresIn;
	private Long id;
	private String tokenType = "Bearer";
}