package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.MilestoneRequest;
import com.agrigov.dto.MilestoneResponse;

public interface MilestoneService {

	MilestoneResponse create(MilestoneRequest request);

	MilestoneResponse update(Long milestoneId, MilestoneRequest request);

	MilestoneResponse get(Long milestoneId);

	List<MilestoneResponse> getAll();

	void delete(Long milestoneId);
}