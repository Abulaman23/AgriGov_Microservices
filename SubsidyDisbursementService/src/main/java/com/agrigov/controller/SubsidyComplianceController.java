package com.agrigov.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.SubsidyComplianceDTO;
import com.agrigov.model.Subsidy;
import com.agrigov.repository.SubsidyRepository;
 
 
@RestController
@RequestMapping("/subsidies")
public class SubsidyComplianceController {
 
    private final SubsidyRepository repository;
 
    public SubsidyComplianceController(SubsidyRepository repository) {
        this.repository = repository;
    }
 
    @GetMapping("/{subsidyId}/compliance-data")
    public SubsidyComplianceDTO getComplianceData(
            @PathVariable Long subsidyId) {
 
        Subsidy subsidy = repository.findById(subsidyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subsidy not found: " + subsidyId));
 
        SubsidyComplianceDTO dto = new SubsidyComplianceDTO();
        dto.setSubsidyId(subsidy.getSubsidyId());
        dto.setStatus(subsidy.getStatus());
        dto.setSchemeId(subsidy.getSchemeId());
        dto.setAmount(subsidy.getAmount());
//        dto.setStatus(subsidy.getStatus());
//        dto.setBudget(subsidy.getBudget());
//        dto.setStartDate(subsidy.getStartDate());
//        dto.setEndDate(subsidy.getEndDate());
//        private Long subsidyId;
//        private Long schemeId;
//        private BigDecimal amount;
//        private String status;
        
//          dto.setSubsidyId(subsidy.getSubsidyId());
//          dto.setSchemeId(subsidy.)
 
        return dto;
    }
}