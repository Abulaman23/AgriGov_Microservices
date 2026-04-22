package com.agrigov.dto;

import java.time.Instant;

import com.agrigov.enums.Role;
import com.agrigov.enums.Status;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private Role role;
	private Status status;
	private Instant lastLoginAt;

}
