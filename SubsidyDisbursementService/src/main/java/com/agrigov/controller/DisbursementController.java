package com.agrigov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.DisbursementRequest;
import com.agrigov.dto.DisbursementResponse;
import com.agrigov.service.DisbursementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/disbursements")
@RequiredArgsConstructor
@Slf4j
public class DisbursementController {

	private final DisbursementService disbursementService;

	// Creates a new Disbursement record.
	@PostMapping("/save")
	public ResponseEntity<DisbursementResponse> create(@Valid @RequestBody DisbursementRequest request) {

		log.info("Creating disbursement with request: {}", request);

		DisbursementResponse response = disbursementService.create(request);

		log.info("Disbursement created successfully with response: {}", response);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Retrieves all Disbursement records.
	@GetMapping("/fetchAll")
	public ResponseEntity<List<DisbursementResponse>> getAll() {

		log.info("Fetching all disbursements");

		return ResponseEntity.ok(disbursementService.getAll());
	}

	// Fetches a Disbursement by its Id.
	@GetMapping("/fetch/{id}")
	public ResponseEntity<DisbursementResponse> getById(@PathVariable Long id) {

		log.info("Fetching disbursement with id: {}", id);

		return ResponseEntity.ok(disbursementService.getById(id));
	}

	// Updates an existing Disbursement.
	@PutMapping("/update/{id}")
	public ResponseEntity<DisbursementResponse> update(@PathVariable Long id,
			@Valid @RequestBody DisbursementRequest request) {

		log.info("Updating disbursement with id: {}", id);

		return ResponseEntity.ok(disbursementService.update(id, request));
	}

	// Deletes a Disbursement by its ID.
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		log.info("Deleting disbursement with id: {}", id);

		disbursementService.delete(id);

		log.info("Disbursement deleted successfully with id: {}", id);

		return ResponseEntity.ok("Disbursement deleted successfully");
	}
}
