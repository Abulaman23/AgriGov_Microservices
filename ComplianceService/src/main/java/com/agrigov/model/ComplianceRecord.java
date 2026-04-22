package com.agrigov.model;

import java.time.LocalDate;

import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "compliances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complianceno", nullable = false)
    private Long complianceId;

    @Column(name = "entity_id", nullable = false)
    private Long entityID;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ComplianceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private ComplianceResult result;

    @Column(name = "notes", nullable = false, length = 255)
    private String notes;

    @Column(name = "date", updatable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }
}



