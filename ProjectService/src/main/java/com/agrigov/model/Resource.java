package com.agrigov.model;

import java.math.BigDecimal;

import com.agrigov.enums.ResourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "resource")

@Data

@NoArgsConstructor

@AllArgsConstructor

public class Resource {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "resource_id", updatable = false)

	private Long resourceId;

	@NotNull(message = "Project is required")

	@ManyToOne(fetch = FetchType.LAZY)

	@JoinColumn(name = "project_id", nullable = false)

	private RuralProject project;

	// FUNDS -> monetary
	// MATERIALS -> physical items/stock
	@NotNull(message = "Type is required")

	@Enumerated(EnumType.STRING)

	@Column(name = "type", nullable = false, length = 20)

	private ResourceType type;

	@NotNull(message = "Quantity is required")

	@DecimalMin(value = "0.00", inclusive = true, message = "Quantity cannot be negative")

	@Column(name = "quantity", nullable = false, precision = 18, scale = 2)

	private BigDecimal quantity;

	// values: PLANNED, ALLOCATED, UTILIZED, ON_HOLD, CANCELLED
	@NotNull(message = "Status is required")

	@Pattern(

			regexp = "PLANNED|ALLOCATED|UTILIZED|ON_HOLD|CANCELLED",

			message = "Status must be one of: PLANNED, ALLOCATED, UTILIZED, ON_HOLD, CANCELLED"

	)

	@Column(name = "status", nullable = false, length = 20)

	private String status;

	public Resource(RuralProject project, ResourceType type, BigDecimal quantity, String status) {

		this.project = project;

		this.type = type;

		this.quantity = quantity;

		this.status = status;

	}

}