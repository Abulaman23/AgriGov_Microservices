package com.agrigov.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "disbursement")
@Data
public class Disbursement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disbursement_id")
	private Long disbursementId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "subsidy_id", nullable = false)
	private Subsidy subsidy;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "officer_id", nullable = false)
//	private User officer;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "status", length = 32, nullable = false)
	private String status;

	@PrePersist
	public void onCreate() {
		this.date = LocalDate.now();
	}
}