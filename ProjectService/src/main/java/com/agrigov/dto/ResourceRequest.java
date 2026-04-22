package com.agrigov.dto;

import java.math.BigDecimal;

import com.agrigov.enums.ResourceType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO used for CREATE/UPDATE.
 * Note: No resourceId here because IDs are server-generated now.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRequest {

    @NotNull(message = "projectId is required")
    private Long projectId;

    @NotNull(message = "type is required")
    private ResourceType type;

    @NotNull(message = "quantity is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "quantity cannot be negative")
    private BigDecimal quantity;

    @NotNull(message = "status is required")
    @Pattern(
        regexp = "PLANNED|ALLOCATED|UTILIZED|ON_HOLD|CANCELLED",
        message = "status must be one of: PLANNED, ALLOCATED, UTILIZED, ON_HOLD, CANCELLED"
    )
    private String status;
}