package com.agrigov.model;

import java.time.LocalDate;

import com.agrigov.enums.SubsidyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subsidies")
@Data
public class Subsidy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subsidy_id")
	private Long subsidyId;

	@Column(name = "scheme_id", nullable = false)
	private Long schemeId;

	@Column(name = "farmer_id", nullable = false)
	private Long farmerId;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "date", updatable = false)
	private LocalDate date;

	@PrePersist
	public void prePersist() {
		this.date = LocalDate.now();
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 40)
	private SubsidyStatus status;
}
