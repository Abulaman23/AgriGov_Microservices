package com.agrigov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.agrigov.dto.SchemeApplicationResponse;
import com.agrigov.model.SchemeApplication;
import com.agrigov.repository.SchemeApplicationRepository;
 
@RestController
@RequestMapping("/application")
public class SchemeSubsidyController {
 
	private final SchemeApplicationRepository repository;
 
	public SchemeSubsidyController(SchemeApplicationRepository repository) {
		this.repository = repository;
	}
 
	@GetMapping("/{schemeId}/{farmerId}/fetch")
	public SchemeApplicationResponse getSchemeAndFarmerId(@PathVariable Long schemeId, @PathVariable Long farmerId) {
 
		SchemeApplication application = repository.findByPolicyscheme_SchemeIDAndFarmerID(schemeId, farmerId)
				.orElseThrow(() -> new IllegalArgumentException("SchemeApplication not found"));
 
		SchemeApplicationResponse dto = new SchemeApplicationResponse();
		dto.setSchemeID(application.getPolicyscheme().getSchemeID());
		dto.setFarmerID(application.getFarmerID());
 
		return dto;
	}
}