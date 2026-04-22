package com.agrigov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.dto.ComplianceRecordRequest;
import com.agrigov.dto.ComplianceRecordResponse;
import com.agrigov.enums.ComplianceResult;
import com.agrigov.enums.ComplianceType;
import com.agrigov.exceptions.ResourceNotFoundException;
import com.agrigov.model.ComplianceRecord;
import com.agrigov.repository.ComplianceRecordRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ComplianceRecordServiceImpl implements ComplianceRecordService {

    private final ComplianceRecordRepository repository;

    private final ComplianceDecisionService decisionService;


    public ComplianceRecordServiceImpl(ComplianceRecordRepository repository,ComplianceDecisionService decisionService) {
        this.repository = repository;
        this.decisionService = decisionService;
    }

    // -------------------------------------------------
    // CREATE
    // -------------------------------------------------
    @Override
    public ComplianceRecordResponse create(ComplianceRecordRequest request) {


    	ComplianceResult result =
                decisionService.decide(
                        request.getType(),
                        request.getEntityID());

        ComplianceRecord record = new ComplianceRecord();
        record.setEntityID(request.getEntityID());
        record.setType(request.getType());
        record.setResult(result);
        record.setNotes(request.getNotes());

        return toResponse(repository.save(record));

    }

    // -------------------------------------------------
    // GET BY ID
    // -------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public ComplianceRecordResponse get(Long id) {

        ComplianceRecord record = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ComplianceRecord not found with id " + id));

        return toResponse(record);
    }

    // -------------------------------------------------
    // GET ALL
    // -------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<ComplianceRecordResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------
    // DELETE
    // -------------------------------------------------
    @Override
    public ComplianceRecordResponse delete(Long id) {

        ComplianceRecord record = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ComplianceRecord not found with id " + id));

        repository.delete(record);
        return toResponse(record);
    }

    // -------------------------------------------------
    // ✅ MAIN BUSINESS API
    // -------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public ComplianceResult getComplianceResult(Long entityId, ComplianceType type) {

        return repository
                .findTopByEntityIDAndTypeOrderByDateDesc(entityId, type)
                .map(ComplianceRecord::getResult)
                .orElse(ComplianceResult.NON_COMPLIANT);
    }

    // -------------------------------------------------
    // BUSINESS RULE ENGINE
    // -------------------------------------------------
    private ComplianceResult evaluateCompliance(Long entityId, ComplianceType type) {

        switch (type) {
            case PROJECT:
                return entityId % 2 == 0
                        ? ComplianceResult.COMPLIANT
                        : ComplianceResult.NON_COMPLIANT;

            case SCHEME:
                return ComplianceResult.COMPLIANT;

            case SUBSIDY:
                return ComplianceResult.NON_COMPLIANT;

            default:
                return ComplianceResult.NON_COMPLIANT;
        }
    }

    // -------------------------------------------------
    // MAPPER
    // -------------------------------------------------
    private ComplianceRecordResponse toResponse(ComplianceRecord record) {

        return new ComplianceRecordResponse(
                record.getComplianceId(),
                record.getEntityID(),
                record.getType(),
                record.getResult(),
                record.getNotes(),
                record.getDate()
        );
    }
}