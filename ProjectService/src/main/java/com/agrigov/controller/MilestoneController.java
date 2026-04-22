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

import com.agrigov.dto.MilestoneRequest;
import com.agrigov.dto.MilestoneResponse;
import com.agrigov.service.MilestoneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
	private final MilestoneService milestoneService;

	public MilestoneController(MilestoneService milestoneService) {
		super();
		this.milestoneService = milestoneService;
	}

	@PostMapping("/save")
	public ResponseEntity<MilestoneResponse> create(@Valid @RequestBody MilestoneRequest request) {

		log.info("Received request to create milestone for projectId={}", request.getProjectId());

		MilestoneResponse saved = milestoneService.create(request);

		log.debug("Milestone created with id={}", saved.getMilestoneId());

		URI uri = URI.create("/api/milestones/" + saved.getMilestoneId());
		return ResponseEntity.created(uri).body(saved);

	}

	@GetMapping("/fetchById/{id}")
	public ResponseEntity<MilestoneResponse> get(@PathVariable("id") Long id) {

		log.info("Fetching milestone with id={}", id);

		MilestoneResponse response = milestoneService.get(id);

		log.debug("Fetched milestone: {}", response);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/fetchAll")
	public ResponseEntity<List<MilestoneResponse>> all() {

		log.info("Fetching all milestones");

		List<MilestoneResponse> list = milestoneService.getAll();

		log.debug("Total milestones found={}", list.size());
		return ResponseEntity.ok(list);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<MilestoneResponse> update(@PathVariable("id") Long id,
			@Valid @RequestBody MilestoneRequest request) {

		log.info("Updating milestone id={}", id);

		MilestoneResponse updated = milestoneService.update(id, request);

		log.debug("Updated milestone id={}", updated.getMilestoneId());
		return ResponseEntity.ok(updated);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		log.warn("Deleting milestone id={}", id);

		milestoneService.delete(id);

		log.info("Deleted milestone id={}", id);
		return ResponseEntity.noContent().build();

	}

}