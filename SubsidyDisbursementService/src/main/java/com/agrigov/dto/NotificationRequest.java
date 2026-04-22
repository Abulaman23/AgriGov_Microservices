package com.agrigov.dto;

import com.agrigov.enums.NotificationCategory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {
    private Long userId; // Removed @NotNull

    @Email(message = "Invalid email format")
    private String email; // Removed @NotBlank

    private Long entityId;

    @NotBlank(message = "Message content is required")
    private String message;

    @NotNull(message = "Category is required")
    private NotificationCategory category;
    
    // REMOVE the manual public void setCategory(String string) method!
}