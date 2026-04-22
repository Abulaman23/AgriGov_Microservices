package com.agrigov.enums;

public enum ApplicationStatus {
    SUBMITTED,    // Farmer has filed the application
    UNDER_REVIEW, // Being checked by a Compliance Officer
    APPROVED,     // Verified and ready for disbursement
    REJECTED,     // Denied due to eligibility or missing docs
    DISBURSED,    // Funds/Subsidy have been paid out
    CANCELLED     // Withdrawn by the farmer
}