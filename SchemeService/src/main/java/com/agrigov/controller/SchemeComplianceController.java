package com.agrigov.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.SchemeComplianceDTO;
import com.agrigov.model.SchemeApplication;
import com.agrigov.repository.SchemeApplicationRepository;


 
@RestController
@RequestMapping("/application")
public class SchemeComplianceController {
 
    private final SchemeApplicationRepository repository;
 
    public SchemeComplianceController(SchemeApplicationRepository repository) {
        this.repository = repository;
    }
 
    @GetMapping("/{schemeId}/compliance-data")
    public SchemeComplianceDTO getComplianceData(
            @PathVariable Long schemeId) {
 
        SchemeApplication scheme = repository.findById(schemeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Project not found: " + schemeId));
 
        SchemeComplianceDTO dto = new SchemeComplianceDTO();
//        dto.setProjectId(project.getProjectId());
//        dto.setStatus(project.getStatus());
//        dto.setBudget(project.getBudget());
//        dto.setStartDate(project.getStartDate());
//        dto.setEndDate(project.getEndDate());
          dto.setSchemeId(scheme.getPolicyscheme().getSchemeID());
          dto.setStatus(scheme.getStatus());
          dto.setBudget(scheme.getPolicyscheme().getBudget());
          dto.setEndDate(scheme.getPolicyscheme().getEndDate());
        return dto;
    }
}