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

import com.agrigov.dto.AuditRequest;
import com.agrigov.dto.AuditResponse;
import com.agrigov.service.AuditService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/audits")
public class AuditController {
	private final AuditService auditService;

	// Constructor Injection.
	public AuditController(AuditService auditService) {
		this.auditService = auditService;
	}

	// Saving the Audit
	@PostMapping("/save") // http://localhost:1234/api/audits/save
	public ResponseEntity<AuditResponse> create(@Valid @RequestBody AuditRequest request) {
		log.info("Audit Saved");// logger
		return ResponseEntity.status(HttpStatus.CREATED).body(auditService.create(request));

	}

	// Fetching Audit by ID
	@GetMapping("fetchById/{id}")
	public ResponseEntity<AuditResponse> get(@PathVariable Long id) { //@ReqParam --> ?id=1&name="Haarish"
		log.info("Getting Audit By Id");
		return ResponseEntity.ok(auditService.get(id));
	}

	// Fetching All Audit
	@GetMapping("fetchAll")
	public ResponseEntity<List<AuditResponse>> all() {
		log.info("Getting all Audits");
		return ResponseEntity.ok(auditService.getAll());
	}

	// Updating Audit By ID
	@PutMapping("update/{id}")
	public ResponseEntity<AuditResponse> update(@PathVariable Long id, @Valid @RequestBody AuditRequest request) {
		log.info("Audit Updated");
		return ResponseEntity.ok(auditService.update(id, request));
	}

	// Deleting Audit By ID
	@DeleteMapping("delete/{id}")
	public ResponseEntity<AuditResponse> delete(@PathVariable Long id) {
		AuditResponse deletedRecord = auditService.get(id); // Storing deleted record
		auditService.delete(id); // Deleting the Audit
		log.info("Audit Deleted"); // Logger
		return ResponseEntity.ok(deletedRecord); // Sending deleted record as a Response
	}
}
