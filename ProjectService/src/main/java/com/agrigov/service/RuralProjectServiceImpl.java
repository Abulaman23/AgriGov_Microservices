package com.agrigov.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.dto.RuralProjectRequest;
import com.agrigov.dto.RuralProjectResponse;
import com.agrigov.dto.RuralProjectResponse.MilestoneSummary;
import com.agrigov.dto.RuralProjectResponse.ResourceSummary;
import com.agrigov.exception.RuralProjectNotFound;
import com.agrigov.model.RuralProject;
import com.agrigov.repo.RuralProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RuralProjectServiceImpl implements RuralProjectService {

	private static final Logger log = LoggerFactory.getLogger(RuralProjectServiceImpl.class);

	private final RuralProjectRepository ruralProjectRepository;

	// -----------------------------
	// CRUD + Queries (return DTOs)
	// -----------------------------

	@Override
	@Transactional
	public RuralProjectResponse create(RuralProjectRequest request) {

		log.info("Creating new rural project: {}", request);

		validateDates(request.getStartDate(), request.getEndDate());

		RuralProject p = new RuralProject();

		apply(p, request);

		p = ruralProjectRepository.save(p);

		log.info("Rural project created successfully with ID: {}", p.getProjectId());

		return toResponse(p);

	}

	@Override
	@Transactional
	public RuralProjectResponse update(Long projectId, RuralProjectRequest request) {

		log.info("Updating rural project ID: {} with data: {}", projectId, request);

		RuralProject project = ruralProjectRepository.findById(projectId).orElseThrow(() -> {
			log.error("Rural project not found with ID: {}", projectId);
			return new RuralProjectNotFound("RuralProject not found: " + projectId);
		});

		validateDates(request.getStartDate(), request.getEndDate());

		apply(project, request);

//		RuralProject updatedProject;

		log.info("Rural project updated successfully with ID: {}", projectId);

		return toResponse(ruralProjectRepository.save(project));
	}

	@Override
	public RuralProjectResponse get(Long projectId) {

		log.info("Fetching rural project with ID: {}", projectId);

		RuralProject p = ruralProjectRepository.findById(projectId).orElseThrow(() -> {
			log.error("Rural project not found with ID: {}", projectId);
			return new RuralProjectNotFound("RuralProject not found: " + projectId);
		});

		log.info("Rural project fetched: {}", p.getProjectId());

		return toResponse(p);

	}

	@Override
	public List<RuralProjectResponse> getAll() {

		log.info("Fetching all rural projects...");

		List<RuralProjectResponse> list = ruralProjectRepository.findAll().stream().map(this::toResponse)
				.collect(Collectors.toList());

		log.info("Total rural projects fetched: {}", list.size());

		return list;

	}

	@Override
	@Transactional
	public void delete(Long projectId) {

		log.warn("Attempting to delete rural project with ID: {}", projectId);

		RuralProject p = ruralProjectRepository.findById(projectId).orElseThrow(() -> {
			log.error("Cannot delete; rural project not found with ID: {}", projectId);
			return new RuralProjectNotFound("RuralProject not found: " + projectId);
		});

		ruralProjectRepository.delete(p);

		log.warn("Rural project deleted successfully with ID: {}", projectId);
	}

	// -----------------------------
	// Mapping helpers
	// -----------------------------

	/** Simple cross-field validation */
	private void validateDates(LocalDate start, LocalDate end) {

		log.debug("Validating dates: start={}, end={}", start, end);

		if (start == null) {
			log.error("Validation failed: startDate is null");
			throw new IllegalArgumentException("startDate is required");
		}
		if (end != null && end.isBefore(start)) {
			log.error("Validation failed: endDate {} is before startDate {}", end, start);
			throw new IllegalArgumentException("endDate cannot be before startDate");
		}

		log.debug("Date validation successful");

	}

//	private void sendProjectCreatedNotification(RuralProject project, User user) {
//
//		NotificationRequest notification = new NotificationRequest();
//
//		notification.setUserId(user.getUserId()); // ✅ Replace with actual logged-in user ID (use 1L then)
//		notification.setEmail(user.getEmail()); // ✅ Replace with real email
//		notification.setEntityId(project.getProjectId());
//		notification.setCategory(NotificationCategory.PROJECT);
//
//		notification.setMessage("Your rural project '" + project.getTitle() + "' (ID: " + project.getProjectId()
//				+ ") has been created successfully.");
//
//		log.info("Sending notification for project ID: {}", project.getProjectId());
//
//		notificationService.createNotification(notification);
//	}

	/** Map RuralProjectRequest -> RuralProject entity */
	private void apply(RuralProject p, RuralProjectRequest req) {
		log.debug("Mapping request to entity: {}", req);

		p.setTitle(req.getTitle());
		p.setDescription(req.getDescription());
		p.setStartDate(req.getStartDate());
		p.setEndDate(req.getEndDate());
		p.setBudget(req.getBudget());
		p.setStatus(req.getStatus());

		log.debug("Entity mapping complete for project ID: {}", p.getProjectId());
	}

	// Map RuralProject entity -> RuralProjectResponse DTO (with lightweight
	// summaries)
	private RuralProjectResponse toResponse(RuralProject p) {
		log.debug("Converting entity to DTO for project ID: {}", p.getProjectId());
		RuralProjectResponse r = new RuralProjectResponse();
		r.setProjectId(p.getProjectId());
		r.setTitle(p.getTitle());
		r.setDescription(p.getDescription());
		r.setStartDate(p.getStartDate());
		r.setEndDate(p.getEndDate());
		r.setBudget(p.getBudget());
		r.setStatus(p.getStatus());

		// Resource summaries (avoid N+1 by planning fetch strategy in
		// repository/service)
		if (p.getResources() != null) {
			Set<ResourceSummary> rs = p.getResources().stream()
					.map(res -> new ResourceSummary(res.getResourceId(),
							res.getType() != null ? res.getType().name() : null, res.getQuantity(), res.getStatus()))
					.collect(Collectors.toSet());
			r.setResources(rs);
		}

		// Milestone summaries
		if (p.getMilestones() != null) {
			Set<MilestoneSummary> ms = p.getMilestones().stream()
					.map(m -> new MilestoneSummary(m.getMilestoneId(), m.getTitle(), m.getDate(), m.getStatus()))
					.collect(Collectors.toSet());
			r.setMilestones(ms);
		}

		log.debug("DTO creation complete for project ID: {}", p.getProjectId());
		return r;
	}
}