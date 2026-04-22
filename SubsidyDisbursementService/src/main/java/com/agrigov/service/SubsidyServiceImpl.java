
package com.agrigov.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrigov.client.SchemeClient;
import com.agrigov.dto.SchemeRequestDTO;
import com.agrigov.dto.SubsidyRequest;
import com.agrigov.dto.SubsidyResponse;
import com.agrigov.enums.SubsidyStatus;
import com.agrigov.exception.ConflictException;
import com.agrigov.exception.ResourceNotFoundException;
import com.agrigov.model.Subsidy;
import com.agrigov.repository.SubsidyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsidyServiceImpl implements SubsidyService {

	private final SubsidyRepository subsidyRepository;
	private final SchemeClient schemeClient;

	@Override
	public SubsidyResponse create(SchemeRequestDTO requestDTO, Double amount, SubsidyStatus status) {

		Long schemeId = requestDTO.getSchemeId();
		Long farmerId = requestDTO.getFarmerId();

		log.info("Creating subsidy for schemeId={} farmerId={}", schemeId, farmerId);

		// Duplicate check
		if (subsidyRepository.existsBySchemeIdAndFarmerId(schemeId, farmerId)) {
			log.warn("Duplicate subsidy found | schemeId={} farmerId={}", schemeId, farmerId);
			throw new ConflictException("Subsidy already exists for this farmer under this scheme");
		}

		// Call Scheme Service
		SchemeRequestDTO application;
		try {
			application = schemeClient.getApplication(schemeId, farmerId);
			log.info("Scheme application found | schemeId={} farmerId={}", schemeId, farmerId);
		} catch (Exception ex) {
			throw new ResourceNotFoundException(
					"No scheme application found for schemeId=" + schemeId + " farmerId=" + farmerId);
		}

		Subsidy subsidy = new Subsidy();
		subsidy.setSchemeId(application.getSchemeId());
		subsidy.setFarmerId(application.getFarmerId());
		subsidy.setAmount(amount);
		subsidy.setStatus(status);

		Subsidy saved = subsidyRepository.save(subsidy);
		log.info("Subsidy created successfully | subsidyId={}", saved.getSubsidyId());
		return toResponse(saved);
	}

	@Override
	public List<SubsidyResponse> getAll() {
		log.info("Fetching all subsidies");
		return subsidyRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	public SubsidyResponse getById(Long subsidyId) {
		log.info("Fetching subsidy by ID | subsidyId={}", subsidyId);
		Subsidy subsidy = subsidyRepository.findById(subsidyId)
				.orElseThrow(() -> new ResourceNotFoundException("Subsidy not found: " + subsidyId));
		log.warn("Subsidy not found | subsidyId={}", subsidyId);
		return toResponse(subsidy);
	}

	@Override
	public SubsidyResponse update(Long subsidyId, SubsidyRequest request) {
		log.info("Updating subsidy | subsidyId={}", subsidyId);
		Subsidy subsidy = subsidyRepository.findById(subsidyId)
				.orElseThrow(() -> new ResourceNotFoundException("Subsidy not found: " + subsidyId));

		subsidy.setAmount(request.getAmount());
		subsidy.setStatus(request.getStatus());

		return toResponse(subsidyRepository.save(subsidy));
	}

	@Override
	public void delete(Long subsidyId) {
		log.info("Deleting subsidy | subsidyId={}", subsidyId);
		if (!subsidyRepository.existsById(subsidyId)) {
			throw new ResourceNotFoundException("Subsidy not found: " + subsidyId);
		}
		log.info("Subsidy deleted successfully | subsidyId={}", subsidyId);
		subsidyRepository.deleteById(subsidyId);
	}

	private SubsidyResponse toResponse(Subsidy subsidy) {
		SubsidyResponse response = new SubsidyResponse();
		response.setSubsidyId(subsidy.getSubsidyId());
		response.setSchemeId(subsidy.getSchemeId());
		response.setFarmerId(subsidy.getFarmerId());
		response.setAmount(subsidy.getAmount());
		response.setDate(subsidy.getDate());
		response.setStatus(subsidy.getStatus());
		return response;
	}
}