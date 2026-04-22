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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "milestone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Remove if you assign IDs manually
	@Column(name = "milestone_id")
	private Long milestoneId;

	// Many milestones belong to one project.
	@NotNull(message = "Project is required")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private RuralProject project;

	@NotBlank(message = "Title cannot be null, empty or blank")
	@Size(min = 3, max = 100, message = "Title length must be between 3 and 100 characters")
	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@NotNull(message = "Date is required")
	@Column(name = "date", nullable = false)
	private LocalDate date;

	// values: PLANNED, IN_PROGRESS, COMPLETED, DELAYED, CANCELLED

	@NotNull(message = "Status is required")
	@Pattern(regexp = "PLANNED|IN_PROGRESS|COMPLETED|DELAYED|CANCELLED", message = "Status must be one of: PLANNED, IN_PROGRESS, COMPLETED, DELAYED, CANCELLED")
	@Column(name = "status", nullable = false, length = 20)
	private String status;

	public Milestone(RuralProject project, String title, LocalDate date, String status) {
		this.project = project;
		this.title = title;
		this.date = date;
		this.status = status;
	}
}