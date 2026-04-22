package com.agrigov.service;

import com.agrigov.dto.PolicySchemeRequest;
import com.agrigov.dto.PolicySchemeResponse;

import java.util.List;

public interface PolicySchemeService {
    PolicySchemeResponse create(PolicySchemeRequest request);
    List<PolicySchemeResponse> getAll();
    PolicySchemeResponse getById(Long schemeID);
    PolicySchemeResponse update(Long schemeID, PolicySchemeRequest request);
    void delete(Long schemeID);
}
