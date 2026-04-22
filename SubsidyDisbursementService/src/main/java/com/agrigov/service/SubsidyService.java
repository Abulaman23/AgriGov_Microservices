package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.SchemeRequestDTO;
import com.agrigov.dto.SubsidyRequest;
import com.agrigov.dto.SubsidyResponse;
import com.agrigov.enums.SubsidyStatus;

public interface SubsidyService {

	SubsidyResponse create(SchemeRequestDTO requestDTO, Double amount, SubsidyStatus status);

	List<SubsidyResponse> getAll();

	SubsidyResponse getById(Long subsidyId);

	SubsidyResponse update(Long subsidyId, SubsidyRequest request);

	void delete(Long subsidyId);
}
