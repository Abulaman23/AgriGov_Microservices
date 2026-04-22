
package com.agrigov.model;

import java.time.LocalDateTime;

import com.agrigov.enums.NotificationCategory;
import com.agrigov.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "notifications", indexes = { @Index(name = "idx_user_notification", columnList = "userId") })
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;

	@NotNull(message = "User ID is required")
	private Long userId;
     
	private Long entityId;

	@NotBlank(message = "Message content is required")
	@Column(length = 500)
	private String message;

	@Enumerated(EnumType.STRING)
	private NotificationCategory category;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	private LocalDateTime createdDate;

}