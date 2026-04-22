package com.agrigov.model;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
	@Id
	@Column(name = "Audit ID", nullable = false)
	private Long auditId;

//	@ManyToOne
//	@JoinColumn(name = "User")
//	private User user;
	

	@Column(name = "Scope", nullable = false, length = 50)
	@NotBlank(message = "Scope Type is required")
	private String scope;
	
	@Column(name = "Findings", nullable = false, length = 50)
	@NotBlank(message = "Findings Result is required")
	private String findings;
	
	@Column(name = "Date", updatable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }
	
	@Column(name = "Status", nullable = false, length = 50)
	@NotBlank(message = "Audit Status is required")
	private String status;
	
}
