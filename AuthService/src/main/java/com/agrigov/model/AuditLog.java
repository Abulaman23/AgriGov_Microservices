package com.agrigov.model;

import java.time.Instant;
import java.util.List;

import com.agrigov.enums.Role;
import com.agrigov.enums.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // What happened (USER_REGISTERED, USER_FETCHED etc.)
    @Column(nullable = false)
    private String event;

    // Who triggered the event (email from JWT)
    @Column(nullable = true)
    private String actor;

    // Optional: target entity id (userId etc.)
    @Column(nullable = true)
    private String target;

    // Additional context (JSON‑like string for flexibility)
    @Column(length = 2000)
    private String metadata;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }
}