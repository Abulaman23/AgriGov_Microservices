package com.agrigov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.ComplianceRecordRequest;
import com.agrigov.dto.ComplianceRecordResponse;
import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;
import com.agrigov.service.ComplianceRecordService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/compliance-records")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ComplianceRecordResponse> create(
            @Valid @RequestBody ComplianceRecordRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<ComplianceRecordResponse>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponse> delete(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.delete(id));
    }

    // ✅ MAIN API
    // /api/compliance-records/result?entityId=1&type=PROJECT
    @GetMapping("/result")
    public ResponseEntity<ComplianceResult> getComplianceResult(
            @RequestParam Long entityId,
            @RequestParam ComplianceType type) {

        return ResponseEntity.ok(
                service.getComplianceResult(entityId, type));
    }
}
