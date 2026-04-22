package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.external.SubsidyComplianceDTO;

@FeignClient(name = "SubsidyDisbursementService",path = "/subsidies")
public interface SubsidyClient {

    @GetMapping("/{subsidyId}/compliance-data")
    SubsidyComplianceDTO getSubsidy(@PathVariable Long subsidyId);
}
