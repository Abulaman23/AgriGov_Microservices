package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
 
import com.agrigov.dto.NotificationRequest;
import com.agrigov.dto.NotificationResponse;
 
 
 
@FeignClient(name = "NOTIFICATIONSERVICE") // Must match the name in Eureka/Config Server
public interface SchemeNotificationClient {
 
    @PostMapping("/api/notifications/send")
    NotificationResponse createNotification(@RequestBody NotificationRequest request);
}
