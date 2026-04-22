package com.agrigov.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * Feign client used to push audit events
 * to the Audit microservice.
 */

@FeignClient(name = "audit-service")
public interface AuditClient {

    @PostMapping("/audit/events")
    void publish(@RequestBody Map<String, Object> event);
}
