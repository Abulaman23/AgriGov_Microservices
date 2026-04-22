package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.ResourceRequest;
import com.agrigov.dto.ResourceResponse;

public interface ResourceService {

	ResourceResponse create(ResourceRequest request);

	ResourceResponse update(Long resourceId, ResourceRequest request);

	ResourceResponse get(Long resourceId);

	List<ResourceResponse> getAll();

	void delete(Long resourceId);
}