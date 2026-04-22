package com.agrigov.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrigov.dto.ReportRequest;
import com.agrigov.dto.ReportResponse;

@Service
public interface ReportService {
	ReportResponse create(ReportRequest request);
    ReportResponse update(Long reportId, ReportRequest request);
    ReportResponse get(Long reportId);
    List<ReportResponse> getAll();
    ReportResponse delete(Long reportId);
}
