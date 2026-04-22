package com.agrigov.controller;

import java.net.URI;
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

import com.agrigov.dto.ResourceRequest;
import com.agrigov.dto.ResourceResponse;
import com.agrigov.service.ResourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

	private final ResourceService resourceService;

	public ResourceController(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@PostMapping("/save")
	public ResponseEntity<ResourceResponse> create(@Valid @RequestBody ResourceRequest request) {
		log.info("Received request to create a new resource for projectId={}", request.getProjectId());
		ResourceResponse saved = resourceService.create(request);
		URI uri = URI.create("/api/resources/" + saved.getResourceId());
		log.debug("Resource created with ID={}", saved.getResourceId());
		return ResponseEntity.created(uri).body(saved);
	}

	@GetMapping("/fetchById/{id}")
	public ResponseEntity<ResourceResponse> get(@PathVariable("id") Long id) {
		log.info("Fetching resource with ID={}", id);

		ResourceResponse response = resourceService.get(id);

		log.debug("Fetched resource: {}", id);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/fetchAll")
	public ResponseEntity<List<ResourceResponse>> all() {

		log.info("Fetching all resources...");

		List<ResourceResponse> responses = resourceService.getAll();

		log.debug("Total resources returned: {}", responses.size());
		return ResponseEntity.ok(responses);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResourceResponse> update(@PathVariable("id") Long id,
			@Valid @RequestBody ResourceRequest request) {

		log.info("Updating resource with ID={}", id);

		ResourceResponse updated = resourceService.update(id, request);

		log.debug("Resource updated successfully: {}", id);
		return ResponseEntity.ok(updated);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		log.warn("Deleting resource with ID={}", id);

		resourceService.delete(id);

		log.info("Resource deleted successfully: {}", id);
		return ResponseEntity.noContent().build();

	}
}