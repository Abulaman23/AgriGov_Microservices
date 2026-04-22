package com.agrigov.dto;

import com.agrigov.enums.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    private Role role;

    // getters and setters
}
