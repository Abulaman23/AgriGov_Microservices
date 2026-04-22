package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.DisbursementRequest;
import com.agrigov.dto.DisbursementResponse;

public interface DisbursementService {
	DisbursementResponse create(DisbursementRequest request);

	DisbursementResponse getById(Long id);

	List<DisbursementResponse> getAll();

	DisbursementResponse update(Long id, DisbursementRequest request);

	void delete(Long id);
}