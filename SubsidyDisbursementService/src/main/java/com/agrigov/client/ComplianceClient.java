package com.agrigov.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@FeignClient(
    name = "COMPLIANCESERVICE",
    path = "/api/compliance-records"
)
public interface ComplianceClient {
 
    @GetMapping("/result")
    String getComplianceResult(
            @RequestParam("entityId") Long entityId,
            @RequestParam("type") String type
    );
}