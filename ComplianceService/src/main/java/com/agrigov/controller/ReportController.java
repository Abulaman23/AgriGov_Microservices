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

import com.agrigov.dto.ReportRequest;
import com.agrigov.dto.ReportResponse;
import com.agrigov.service.ReportService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Data Annotation for Logger
@RestController // @Controller + @ResponseBody --> returns XML OR JSON
@RequestMapping("/api/reports") // URL
public class ReportController {

	private final ReportService reportService;

	// Constructor Injection
	public ReportController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	// Saving Report
	@PostMapping("/save")
	public ResponseEntity<ReportResponse> create(@Valid @RequestBody ReportRequest report) {
		log.info("Report Saved");
		return ResponseEntity.status(HttpStatus.CREATED).body(reportService.create(report));
	}

	// Fetching Report by ID
	@GetMapping("fetchById/{id}")
	public ResponseEntity<ReportResponse> get(@PathVariable Long id) {
		log.info("Report fetched by Id");
		return ResponseEntity.ok(reportService.get(id));
	}

	// Fetching all the reports.
	@GetMapping("fetchAll")
	public ResponseEntity<List<ReportResponse>> all() {
		log.info("Fetching all");
		return ResponseEntity.ok(reportService.getAll());
	}

	// Updating report by ID.
	@PutMapping("update/{id}")
	public ResponseEntity<ReportResponse> update(@PathVariable Long id, @Valid @RequestBody ReportRequest request) {
		log.info("Updating Report");
		return ResponseEntity.ok(reportService.update(id, request));
	}

	// Deleting Report by ID.
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ReportResponse> delete(@PathVariable Long id) {

		ReportResponse deletedRecord = reportService.get(id);
		reportService.delete(id);
		log.info("Deleting Report");
		return ResponseEntity.ok(deletedRecord);
	}
}
