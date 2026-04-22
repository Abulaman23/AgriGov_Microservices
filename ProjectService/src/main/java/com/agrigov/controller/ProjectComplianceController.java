package com.agrigov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.ProjectComplianceDTO;
import com.agrigov.model.RuralProject;
import com.agrigov.repo.RuralProjectRepository;

@RestController
@RequestMapping("/api/rural-projects")
public class ProjectComplianceController {

    private final RuralProjectRepository repository;

    public ProjectComplianceController(RuralProjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{projectId}/compliance-data")
    public ProjectComplianceDTO getComplianceData(
            @PathVariable Long projectId) {

        RuralProject project = repository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Project not found: " + projectId));

        ProjectComplianceDTO dto = new ProjectComplianceDTO();
        dto.setProjectId(project.getProjectId());
        dto.setStatus(project.getStatus());
        dto.setBudget(project.getBudget());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());

        return dto;
    }
}