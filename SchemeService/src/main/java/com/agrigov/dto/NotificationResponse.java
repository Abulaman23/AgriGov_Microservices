package com.agrigov.dto;

import java.time.LocalDateTime;

import com.agrigov.enums.NotificationCategory;
import com.agrigov.enums.NotificationStatus;
 
import lombok.Data;
// this is notify response
@Data
public class NotificationResponse {
	private Long notificationId;
	private Long userId;
	private Long entityId;
	private String message;
	private NotificationCategory category;
	private NotificationStatus status;
	private LocalDateTime createdDate;
 
}
