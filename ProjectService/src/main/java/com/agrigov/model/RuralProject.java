package com.agrigov.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rural_project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuralProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long projectId;

	@NotBlank(message = "Title cannot be null, empty or blank")
	@Size(min = 3, max = 100, message = "Title length must be between 3 and 100 characters")
	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Size(max = 2000, message = "Description cannot exceed 2000 characters")
	@Column(name = "description", length = 2000)
	private String description;

	@NotNull(message = "Start date is required")
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@NotNull(message = "Budget is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "Budget cannot be negative")
	@Column(name = "budget", nullable = false, precision = 18, scale = 2)
	private BigDecimal budget;

	// Suggested values: PLANNED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED You can

	@NotBlank(message = "Status cannot be null, empty or blank")
	@Pattern(regexp = "PLANNED|IN_PROGRESS|COMPLETED|ON_HOLD|CANCELLED", message = "Status must be one of: PLANNED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED")
	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@OneToMany(mappedBy = "project", orphanRemoval = true)
	private List<Resource> resources = new ArrayList<>();
	@OneToMany(mappedBy = "project", orphanRemoval = true)
	private List<Milestone> milestones = new ArrayList<>();

//	@OneToMany(mappedBy = "ruralProject", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<ComplianceRecord> complianceRecords = new ArrayList<>();
	
	private Long complianceId;
	private String result;

}
