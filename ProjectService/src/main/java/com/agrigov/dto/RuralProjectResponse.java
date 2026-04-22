
package com.agrigov.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuralProjectResponse {

	private Long projectId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate; // can be null if ongoing
	private BigDecimal budget;
	private String status; // PLANNED, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED

	private Set<ResourceSummary> resources;

	private Set<MilestoneSummary> milestones;
	private ProjectComplianceDTO complianceStatus;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResourceSummary {
		private Long resourceId;
		private String type; // FUNDS or MATERIALS
		private BigDecimal quantity; // as stored (DECIMAL)
		private String status; // PLANNED, ALLOCATED, UTILIZED, ON_HOLD, CANCELLED
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MilestoneSummary {
		private Long milestoneId;
		private String title;
		private LocalDate date;
		private String status; // PLANNED, IN_PROGRESS, COMPLETED, DELAYED, CANCELLED
	}
}