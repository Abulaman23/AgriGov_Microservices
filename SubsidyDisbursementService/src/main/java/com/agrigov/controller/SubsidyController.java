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

import com.agrigov.dto.SchemeRequestDTO;
import com.agrigov.dto.SubsidyRequest;
import com.agrigov.dto.SubsidyResponse;
import com.agrigov.service.SubsidyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/subsidies")
@RequiredArgsConstructor
@Slf4j
public class SubsidyController {

	private final SubsidyService subsidyService;

	// Create Subsidy
	@PostMapping("/save")
	public ResponseEntity<SubsidyResponse> create(@Valid @RequestBody SubsidyRequest request) {

		log.info("Request to create subsidy received: {}", request);

		SchemeRequestDTO schemeRequestDTO = new SchemeRequestDTO();
		schemeRequestDTO.setSchemeId(request.getSchemeId());
		schemeRequestDTO.setFarmerId(request.getFarmerId());

		SubsidyResponse response = subsidyService.create(schemeRequestDTO, request.getAmount(), request.getStatus());

		log.info("Subsidy created successfully with ID: {}", response);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Fetch All Subsidies
	@GetMapping("/fetchAll")
	public ResponseEntity<List<SubsidyResponse>> getAll() {

		log.info("Fetching all subsidies");

		List<SubsidyResponse> response = subsidyService.getAll();

		log.info("Total subsidies found: {}", response.size());

		return ResponseEntity.ok(response);
	}

	// Fetch Subsidy by ID
	@GetMapping("/fetch/{id}")
	public ResponseEntity<SubsidyResponse> getById(@PathVariable Long id) {

		log.info("Fetching subsidy with ID: {}", id);

		SubsidyResponse response = subsidyService.getById(id);

		log.info("Subsidy fetched successfully for ID: {}", id);

		return ResponseEntity.ok(response);
	}

	// Update Subsidy
	@PutMapping("/update/{id}")
	public ResponseEntity<SubsidyResponse> update(@PathVariable Long id, @Valid @RequestBody SubsidyRequest request) {

		log.info("Updating subsidy with ID: {}", id);

		SubsidyResponse response = subsidyService.update(id, request);

		log.info("Subsidy updated successfully for ID: {}", id);

		return ResponseEntity.ok(response);
	}

	// Delete Subsidy
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		log.warn("Deleting subsidy with ID: {}", id);

		subsidyService.delete(id);

		log.info("Subsidy deleted successfully for ID: {}", id);

		return ResponseEntity.ok("Subsidy deleted successfully");
	}
}

//package com.agrigov.controller;

//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.agrigov.dto.SchemeRequestDTO;
//import com.agrigov.dto.SubsidyRequest;
//import com.agrigov.dto.SubsidyResponse;
//import com.agrigov.service.SubsidyService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/subsidies")
//@RequiredArgsConstructor
//@Slf4j
//public class SubsidyController {
//
//	private final SubsidyService subsidyService;
//
//	// Create Subsidy
//	@PostMapping("/save")
//	public ResponseEntity<SubsidyResponse> create(@Valid @RequestBody SubsidyRequest request) {
//
//		log.info("Creating subsidy with request: {}", request);
//
//		SchemeRequestDTO schemeRequestDTO = new SchemeRequestDTO();
//		schemeRequestDTO.setSchemeId(request.getSchemeId());
//		schemeRequestDTO.setFarmerId(request.getFarmerId());
//
//		SubsidyResponse response = subsidyService.create(schemeRequestDTO, request.getAmount(), request.getStatus());
//
//		log.info("Subsidy created successfully");
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	}
//
//	// Fetch all
//	@GetMapping("/fetchAll")
//	public ResponseEntity<List<SubsidyResponse>> getAll() {
//		return ResponseEntity.ok(subsidyService.getAll());
//	}
//
//	// Fetch by ID
//	@GetMapping("/fetch/{id}")
//	public ResponseEntity<SubsidyResponse> getById(@PathVariable Long id) {
//		return ResponseEntity.ok(subsidyService.getById(id));
//	}
//
//	// Update
//	@PutMapping("/update/{id}")
//	public ResponseEntity<SubsidyResponse> update(@PathVariable Long id, @Valid @RequestBody SubsidyRequest request) {
//		return ResponseEntity.ok(subsidyService.update(id, request));
//	}
//
//	// Delete
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<String> delete(@PathVariable Long id) {
//		subsidyService.delete(id);
//		return ResponseEntity.ok("Subsidy deleted successfully");
//	}
//}
