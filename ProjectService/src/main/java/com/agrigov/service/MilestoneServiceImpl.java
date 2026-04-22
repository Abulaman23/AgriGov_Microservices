package com.agrigov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrigov.dto.MilestoneRequest;
import com.agrigov.dto.MilestoneResponse;
import com.agrigov.exception.MilestoneNotFound;
import com.agrigov.model.Milestone;
import com.agrigov.model.RuralProject;
import com.agrigov.repo.MilestoneRepository;
import com.agrigov.repo.RuralProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

	private static final Logger log = LoggerFactory.getLogger(MilestoneServiceImpl.class);

	private final MilestoneRepository milestoneRepository;
	private final RuralProjectRepository ruralProjectRepository;

	@Override
	@Transactional
	public MilestoneResponse create(MilestoneRequest request) {

		log.info("Creating new milestone for projectId={}", request.getProjectId());
		Milestone milestone = new Milestone();
		apply(milestone, request);

		milestone = milestoneRepository.save(milestone);
		log.debug("Milestone saved with id={}", milestone.getMilestoneId());

		return toResponse(milestone);

	}

	@Override
	@Transactional
	public MilestoneResponse update(Long milestoneId, MilestoneRequest request) {

		log.info("Updating milestoneId={}", milestoneId);

		Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> {
			log.error("Milestone not found with id={}", milestoneId);
			return new MilestoneNotFound("Milestone not found: " + milestoneId);
		});

		apply(milestone, request);
		milestone = milestoneRepository.save(milestone);

		log.debug("Milestone updated: id={}", milestone.getMilestoneId());
		return toResponse(milestone);

	}

	@Override
	public MilestoneResponse get(Long milestoneId) {

		log.info("Fetching milestoneId={}", milestoneId);

		Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> {
			log.error("Milestone not found with id={}", milestoneId);
			return new MilestoneNotFound("Milestone not found: " + milestoneId);
		});

		return toResponse(milestone);

	}

	@Override
	public List<MilestoneResponse> getAll() {

		log.info("Fetching all milestones");
		return milestoneRepository.findAll().stream().peek(m -> log.debug("Found milestone id={}", m.getMilestoneId()))
				.map(this::toResponse).collect(Collectors.toList());

	}

	@Override
	@Transactional
	public void delete(Long milestoneId) {

		log.warn("Deleting milestoneId={}", milestoneId);

		Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> {
			log.error("Milestone not found with id={}", milestoneId);
			return new MilestoneNotFound("Milestone not found: " + milestoneId);
		});

		milestoneRepository.delete(milestone);
		log.info("Milestone deleted id={}", milestoneId);

	}

	/** Map MilestoneRequest -> Milestone entity (no ID set here) */
	private void apply(Milestone entity, MilestoneRequest dto) {
		// Resolve project (FK)

		log.debug("Applying request to milestone entity. projectId={}", dto.getProjectId());

		RuralProject project = ruralProjectRepository.findById(dto.getProjectId()).orElseThrow(() -> {
			log.error("Project not found with id={}", dto.getProjectId());
			return new MilestoneNotFound("RuralProject not found: " + dto.getProjectId());
		});

		entity.setProject(project);
		entity.setTitle(dto.getTitle());
		entity.setDate(dto.getDate());
		entity.setStatus(dto.getStatus());

	}

	/** Map entity -> response DTO */
	private MilestoneResponse toResponse(Milestone e) {
		log.debug("Mapping milestone entity to response id={}", e.getMilestoneId());
		MilestoneResponse r = new MilestoneResponse();
		r.setMilestoneId(e.getMilestoneId());
		r.setTitle(e.getTitle());
		r.setDate(e.getDate());
		r.setStatus(e.getStatus());
		if (e.getProject() != null) {
			r.setProjectId(e.getProject().getProjectId());
			r.setProjectTitle(e.getProject().getTitle());
		}
		return r;
	}

}