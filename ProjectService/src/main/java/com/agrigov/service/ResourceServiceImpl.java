package com.agrigov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.dto.ResourceRequest;
import com.agrigov.dto.ResourceResponse;
import com.agrigov.exception.ResourceNotFoundException;
import com.agrigov.model.Resource;
import com.agrigov.model.RuralProject;
import com.agrigov.repo.ResourceRepository;
import com.agrigov.repo.RuralProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

	private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

	private final ResourceRepository resourceRepository;
	private final RuralProjectRepository ruralProjectRepository;

	@Override
	@Transactional
	public ResourceResponse create(ResourceRequest request) {
		log.info("Creating new resource for projectId={}", request.getProjectId());
		Resource resource = new Resource();
		apply(resource, request);
		resource = resourceRepository.save(resource); // ID generated here

		log.debug("Resource created successfully with ID={}", resource.getResourceId());
		return toResponse(resource);
	}

	@Override
	@Transactional
	public ResourceResponse update(Long resourceId, ResourceRequest request) {
		log.info("Updating resource with ID={}", resourceId);
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> {
			log.error("Resource not found: {}", resourceId);
			return new ResourceNotFoundException("Resource not found: " + resourceId);
		});
		apply(resource, request);
		resource = resourceRepository.save(resource);
		log.debug("Resource updated successfully: ID={}", resource.getResourceId());
		return toResponse(resource);
	}

	@Override
	public ResourceResponse get(Long resourceId) {
		log.info("Fetching resource with ID={}", resourceId);
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> {
			log.error("Resource not found: {}", resourceId);
			return new ResourceNotFoundException("Resource not found: " + resourceId);
		});

		log.debug("Resource fetched: {}", resourceId);
		return toResponse(resource);
	}

	@Override
	public List<ResourceResponse> getAll() {
		log.info("Fetching all resources...");

		List<ResourceResponse> list = resourceRepository.findAll().stream().map(this::toResponse)
				.collect(Collectors.toList());

		log.debug("Total resources fetched: {}", list.size());
		return list;

	}

	@Override
	@Transactional
	public void delete(Long resourceId) {
		log.warn("Deleting resource with ID={}", resourceId);
		Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> {
			log.error("Resource not found: {}", resourceId);
			return new ResourceNotFoundException("Resource not found: " + resourceId);
		});
		resourceRepository.delete(resource);
		log.info("Resource deleted successfully: {}", resourceId);
	}

	// -------- mapping --------

	/** Map ResourceRequest -> Resource entity (no ID set here) */
	private void apply(Resource entity, ResourceRequest dto) {
		// Resolve project (FK)
		log.debug("Applying ResourceRequest to Resource entity (projectId={})", dto.getProjectId());
		RuralProject project = ruralProjectRepository.findById(dto.getProjectId()).orElseThrow(() -> {
			log.error("RuralProject not found: {}", dto.getProjectId());
			return new ResourceNotFoundException("Resource not found for projectId: " + dto.getProjectId());
		});
		entity.setProject(project);

		entity.setType(dto.getType());
		entity.setQuantity(dto.getQuantity());
		entity.setStatus(dto.getStatus());
	}

	/** Map entity -> response DTO */
	private ResourceResponse toResponse(Resource e) {
		ResourceResponse r = new ResourceResponse();
		r.setResourceId(e.getResourceId());
		r.setType(e.getType());
		r.setQuantity(e.getQuantity());
		r.setStatus(e.getStatus());
		if (e.getProject() != null) {
			r.setProjectId(e.getProject().getProjectId());
			r.setProjectTitle(e.getProject().getTitle());
		}
		return r;
	}
}
