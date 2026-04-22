package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.external.SchemeComplianceDTO;

@FeignClient(
	    name = "SCHEMESERVICE",
	    path = "/application"
	)
public interface SchemeClient {
 
    @GetMapping("/{schemeId}/compliance-data")
    SchemeComplianceDTO getComplianceData(@PathVariable("schemeId") Long schemeId);
}
