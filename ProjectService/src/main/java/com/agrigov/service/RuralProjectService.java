package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.RuralProjectRequest;
import com.agrigov.dto.RuralProjectResponse;

public interface RuralProjectService {
	RuralProjectResponse create(RuralProjectRequest request);

	RuralProjectResponse update(Long projectId, RuralProjectRequest request);

	RuralProjectResponse get(Long projectId);

	List<RuralProjectResponse> getAll();

	void delete(Long projectId);
}
