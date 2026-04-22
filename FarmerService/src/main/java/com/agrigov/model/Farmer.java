package com.agrigov.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Farmer")
@Data
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmerId;

    @Column(nullable = false, length = 100) // Name is mandatory
    private String name;

    @Column(nullable = false) // DOB is mandatory for gov records
    private LocalDate dob;

    @Column(length = 10) // e.g., "Male", "Female", "Other"
    private String gender;

    @Column(length = 500) // Addresses can be long
    private String address;

    @Column(name = "contact_info", unique = true, length = 15) // Unique mobile/phone
    private String contactInfo;

    @Column(length = 1000) // Land details might contain survey numbers
    private String landDetails;

    @Column(nullable = false, length = 20) // e.g., "ACTIVE", "PENDING"
    private String status;
}