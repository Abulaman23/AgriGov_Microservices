package com.agrigov.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.RuralProjectRequest;
import com.agrigov.dto.RuralProjectResponse;
import com.agrigov.service.RuralProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rural-projects")
public class RuralProjectController {

	private static final Logger log = LoggerFactory.getLogger(RuralProjectController.class);

	private final RuralProjectService ruralProjectService;

	public RuralProjectController(RuralProjectService ruralProjectService) {
		this.ruralProjectService = ruralProjectService;
	}

	// Create a new rural project.
	@PostMapping("/save") // http://localhost:1234/api/rural-projects/save
	public ResponseEntity<RuralProjectResponse> create(@Valid @RequestBody RuralProjectRequest request) {

		log.info("Received request to create a new rural project: {}", request);

		RuralProjectResponse response = ruralProjectService.create(request);

		log.info("Successfully created rural project with ID: {}", response.getProjectId());
		return ResponseEntity.ok(response);

	}

	// Fetch a project by its unique identifier.
	@GetMapping("fetchById/{id}")
	public ResponseEntity<RuralProjectResponse> get(@PathVariable Long id) {

		log.info("Fetching rural project with ID: {}", id);

		RuralProjectResponse response = ruralProjectService.get(id);

		log.info("Fetched rural project: {}", response);
		return ResponseEntity.ok(response);

	}

	// Fetch all rural projects.
	@GetMapping("fetchAll")
	public ResponseEntity<List<RuralProjectResponse>> all() {

		log.info("Fetching all rural projects...");

		List<RuralProjectResponse> projects = ruralProjectService.getAll();

		log.info("Total rural projects fetched: {}", projects.size());
		return ResponseEntity.ok(projects);

	}

	// Update an existing rural project by its ID.
	@PutMapping("updateProject/{id}")
	public ResponseEntity<RuralProjectResponse> update(@PathVariable Long id,
			@Valid @RequestBody RuralProjectRequest request) {

		log.info("Updating rural project with ID: {} | New data: {}", id, request);

		RuralProjectResponse response = ruralProjectService.update(id, request);

		log.info("Successfully updated rural project ID: {}", id);
		return ResponseEntity.ok(response);

	}

	// Delete a project by its ID.
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		log.warn("Request received to delete rural project with ID: {}", id);

		ruralProjectService.delete(id);

		log.warn("Successfully deleted rural project with ID: {}", id);
		return ResponseEntity.noContent().build();

	}
}