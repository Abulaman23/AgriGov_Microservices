package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.external.ProjectComplianceDTO;

//@FeignClient(name = "project-service")
@FeignClient(
	    name = "PROJECTSERVICE",
	    path = "/api/rural-projects"
	)
public interface ProjectClient {

    @GetMapping("/{projectId}/compliance-data")
    ProjectComplianceDTO getComplianceData(@PathVariable Long projectId);
}
