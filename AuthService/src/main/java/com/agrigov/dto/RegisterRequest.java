package com.agrigov.dto;

import com.agrigov.enums.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
}
