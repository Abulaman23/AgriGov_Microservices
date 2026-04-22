package com.agrigov.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.agrigov.dto.FarmerDocumentResponse;
import com.agrigov.service.FarmerDocumentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/documents")
@Slf4j
@RequiredArgsConstructor
public class FarmerDocumentController {

	private final FarmerDocumentService service;

	/**
	 * Upload a document. Note: Gateway routes /documents/** to this service.
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FarmerDocumentResponse uploadDocument(@RequestParam("farmerId") Long farmerId,
			@RequestParam("docType") String docType, @RequestParam("file") MultipartFile file) {

		log.info("Received upload request for Farmer ID: {} - Type: {}", farmerId, docType);
		return service.uploadDocument(farmerId, docType, file);
	}

	/**
	 * Feign Client Endpoint: FarmerService calls this to aggregate data. Path
	 * matches: /documents/farmer/{farmerId}
	 */
	@GetMapping("/farmer/{farmerId}")
	public List<FarmerDocumentResponse> getDocumentsByFarmer(@PathVariable("farmerId") Long farmerId) {
		log.info("Inter-service call: Fetching documents for farmer ID: {}", farmerId);
		return service.getDocumentsByFarmer(farmerId);
	}

	@PatchMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FarmerDocumentResponse updateDocument(@PathVariable("id") Long documentId,
			@RequestParam(value = "docType", required = false) String docType,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		log.info("Updating metadata/file for Document ID: {}", documentId);
		return service.updateDocument(documentId, docType, file);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteDocument(@PathVariable("id") Long documentId) {
		log.warn("Deleting document record and local file for ID: {}", documentId);
		service.deleteDocument(documentId);
		return "Document with ID " + documentId + " deleted successfully";
	}

	@GetMapping("/fetch/{id}")
	public FarmerDocumentResponse getDocumentById(@PathVariable("id") Long documentId) {
		return service.getDocumentById(documentId);
	}

	@GetMapping("/fetchByStatus/{status}")
	public List<FarmerDocumentResponse> getDocumentsByStatus(@PathVariable("status") String status) {
		return service.getDocumentsByStatus(status);
	}
}