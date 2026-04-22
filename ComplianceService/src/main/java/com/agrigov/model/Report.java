package com.agrigov.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	@Id
	@Column(name = "Report ID", nullable = false)
	private Long reportId;

	@Column(name = "Scope", nullable = false, length = 50)
	@NotBlank(message = "Scope Type is required")
	private String scope;
	
	@Column(name = "Date", updatable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }
	@Column(name = "Metrics", nullable = false, length = 50)
	@NotBlank(message = "Metrics  is required")
	private String metrics;
//	
//	//Mapping of Report to User
//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "userId")
//	private User user;
//
//	//Mapping of Report to Audit
//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "auditId")
//	private Audit audit;

}
