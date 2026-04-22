package com.agrigov.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.agrigov.compliance.repository.UserRepository;
import com.agrigov.dto.AuditRequest;
import com.agrigov.dto.AuditResponse;
import com.agrigov.exceptions.ResourceNotFoundException;
import com.agrigov.model.Audit;
import com.agrigov.repository.AuditRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

	private final AuditRepository auditRepository;
//	private final UserRepository userRepository;

	// Constructor Injection
	public AuditServiceImpl(AuditRepository auditRepository) {
		this.auditRepository = auditRepository;
//		this.userRepository = userRepository;
	}

	@Override
	public AuditResponse create(AuditRequest request) {

		log.info("creating new audit");
		Audit ad = new Audit();
		applyPatch(ad, request);

//		if (ad.getUser().getUserId() == null || ad.getScope() == null || ad.getFindings() == null
//				|| ad.getStatus() == null) {
//			throw new IllegalArgumentException("Missing Mandatory fields for Audit create");
//		}
		ad = auditRepository.saveAndFlush(ad);
		return toResponse(ad);
	}

	@Override
	@Transactional
	public AuditResponse update(Long auditId, AuditRequest request) {

		log.info("updating new audit");
		Audit ad = auditRepository.findById(auditId)
				.orElseThrow(() -> new ResourceNotFoundException("Audit not found: " + auditId));

		// Keep path ID as source of truth (normalize inbound request)
		if (request.getAuditId() != null && !Objects.equals(request.getAuditId(), ad.getAuditId())) {
			request.setAuditId(ad.getAuditId());
		}

		applyPatch(ad, request);

		try {
			ad = auditRepository.saveAndFlush(ad); // flush to surface constraint violations here
		} catch (DataIntegrityViolationException ex) {
			String msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
			throw new IllegalArgumentException("Invalid ComplianceRecord update: " + msg, ex);
		}

		return toResponse(ad);
	}

	@Override
	@Transactional(readOnly = true)
	public AuditResponse get(Long auditId) {
		log.info("getting  audit");
		Audit ad = auditRepository.findById(auditId)
				.orElseThrow(() -> new ResourceNotFoundException("Audit not found: " + auditId));
		return toResponse(ad);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditResponse> getAll() {
		log.info("getting all audit");
		return auditRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	public AuditResponse delete(Long auditId) {
		Audit ad = auditRepository.findById(auditId)
				.orElseThrow(() -> new ResourceNotFoundException("Audit not found: " + auditId));
		auditRepository.delete(ad);
		log.info("deleting  audit");
		return toResponse(ad);
	}

	private void applyPatch(Audit ad, AuditRequest req) {
		// ID — set only if provided (and only if you are NOT using DB-generated ID)

		if (req.getAuditId() != null)
			ad.setAuditId(req.getAuditId());

//		if (req.getUserId() != null) {
//			User user = userRepository.findById(req.getUserId())
//					.orElseThrow(() -> new ResourceNotFoundException("User not found: " + req.getUserId()));
//			ad.setUser(user);
//		}

		if (req.getScope() != null)
			ad.setScope(req.getScope());
		if (req.getFindings() != null)
			ad.setFindings(req.getFindings());
		if (req.getStatus() != null)
			ad.setStatus(req.getStatus());

	}

	private AuditResponse toResponse(Audit ad) {
		AuditResponse r = new AuditResponse();
		r.setAuditId(ad.getAuditId());
//		r.setUserId(ad.getUser().getUserId());
		r.setScope(ad.getScope());
		r.setFindings(ad.getFindings());
		r.setDate(ad.getDate());
		r.setStatus(ad.getStatus());

		if (ad.getAuditId() != null) {
			r.setAuditId(ad.getAuditId());
		}

		return r;
	}
}