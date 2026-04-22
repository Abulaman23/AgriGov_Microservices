package com.agrigov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.agrigov.compliance.repository.UserRepository;
import com.agrigov.dto.ReportRequest;
import com.agrigov.dto.ReportResponse;
import com.agrigov.exceptions.ReportResponseNotFoundException;
import com.agrigov.exceptions.ResourceNotFoundException;
import com.agrigov.model.Audit;
import com.agrigov.model.Report;
import com.agrigov.repository.AuditRepository;
import com.agrigov.repository.ReportRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	private final ReportRepository reportRepository;
//	private final UserRepository userRepository;
	private final AuditRepository auditRepository;
	

	@Override
	@Transactional
	public ReportResponse create(ReportRequest request) {
		log.info("creating new report");
		Report report = new Report();
		applyPatch(report, request);
		report = reportRepository.save(report);
		return toResponse(report);
	}

	@Override
	@Transactional
	public ReportResponse update(Long reportId, ReportRequest request) {
		log.info("updating report");
		Report report = reportRepository.findById(reportId).orElseThrow(() -> new ReportResponseNotFoundException("Report not found: "+reportId));
		applyPatch(report, request);
		report = reportRepository.save(report);
		return toResponse(report);
	}

	@Override
	public ReportResponse get(Long reportId) {
		log.info("getting report");
		Report report = reportRepository.findById(reportId).orElseThrow(() -> new ReportResponseNotFoundException("Report not found: "+reportId));
		return toResponse(report);
	}

	@Override
	public List<ReportResponse> getAll() {
		log.info("getting all report");
		return reportRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ReportResponse delete(Long reportId) {
		log.info("deleting report");
		Report report = reportRepository.findById(reportId).orElseThrow(() -> new ReportResponseNotFoundException("Report not found: "+reportId));
		reportRepository.delete(report);
		return toResponse(report);
	}
	private void applyPatch(Report re, ReportRequest req) {
        // ID — set only if provided (and only if you are NOT using DB-generated ID)

        if (req.getReportId() != null) re.setReportId(req.getReportId());
        if (req.getScope() != null) re.setScope(req.getScope());
        if (req.getMetrics() != null) re.setMetrics(req.getMetrics());

        // Associations — set only if IDs provided
//        if (req.getUserId() != null) {
//            User us = userRepository.findById(req.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + req.getUserId()));
//            re.setUser(us);
//        }
//        if (req.getAuditId() != null) {
//            Audit ad = auditRepository.findById(req.getAuditId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + req.getUserId()));
//            re.setAudit(ad);
//        }
	}
	private ReportResponse toResponse(Report re) {
        ReportResponse r = new ReportResponse();
        r.setReportId(re.getReportId());
        r.setScope(re.getScope());
        r.setMetrics(re.getMetrics());
        r.setDate(re.getDate());
//
//        if (re.getAudit() != null && re.getAudit().getAuditId() != null) {
//            r.setAuditId(re.getAudit().getAuditId());
//        }
//        if (re.getUser() != null && re.getUser().getUserId() != null) {
//            r.setUserId(re.getUser().getUserId());
//        }

        return r;
	}
}
