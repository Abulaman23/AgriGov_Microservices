package com.agrigov.dto;

import lombok.Data;

@Data
public class FarmerResponse {
    private Long farmerId;
    private String name;
    private String status; // CRITICAL: Used to check if farmer is ACTIVE
    private String contactInfo;
}
 