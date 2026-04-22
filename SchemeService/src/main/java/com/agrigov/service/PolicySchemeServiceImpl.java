package com.agrigov.service;

import com.agrigov.dto.PolicySchemeRequest;
import com.agrigov.dto.PolicySchemeResponse;
import com.agrigov.exception.ResourceNotFoundException;
import com.agrigov.model.PolicyScheme;
import com.agrigov.repository.PolicySchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PolicySchemeServiceImpl implements PolicySchemeService {

    private final PolicySchemeRepository repository;

    @Override
    public PolicySchemeResponse create(PolicySchemeRequest request) {
        if (repository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new IllegalArgumentException("Policy Scheme with this title already exists");
        }
        PolicyScheme entity = fromRequest(request, new PolicyScheme());
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolicySchemeResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PolicySchemeResponse getById(Long SchemeID) {
        PolicyScheme entity = repository.findById(SchemeID)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyScheme not found: " + SchemeID));
        return toResponse(entity);
    }

    @Override
    public PolicySchemeResponse update(Long SchemeID, PolicySchemeRequest request) {
        PolicyScheme entity = repository.findById(SchemeID)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyScheme not found: " + SchemeID));
        entity = fromRequest(request, entity);  // overwrite from request
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long SchemeID) {
        if (!repository.existsById(SchemeID)) {
            throw new ResourceNotFoundException("PolicyScheme not found: " + SchemeID);
        }
        repository.deleteById(SchemeID);
    }

    /* ------------ Mapping helpers ------------ */

    private PolicySchemeResponse toResponse(PolicyScheme e) {
        return PolicySchemeResponse.builder()
                .schemeID(e.getSchemeID())
                .title(e.getTitle())
                .description(e.getDescription())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .budget(e.getBudget())
                .status(e.getStatus())
                .build();
    }

    private PolicyScheme fromRequest(PolicySchemeRequest r, PolicyScheme target) {
        target.setTitle(r.getTitle());
        target.setDescription(r.getDescription());
        target.setStartDate(r.getStartDate());
        target.setEndDate(r.getEndDate());
        target.setBudget(r.getBudget());
        target.setStatus(r.getStatus());
        return target;
    }
}