package com.agrigov.enums;

public enum SchemeStatus {
    DRAFT,      // Initial stage, not visible to farmers
    ACTIVE,     // Open for applications
    CLOSED,     // Application window has ended
    EXPIRED,    // Budget exhausted or end date passed
    SUSPENDED   // Temporarily halted by Admin/Compliance
}
