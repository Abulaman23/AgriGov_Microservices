package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.SchemeApplicationRequest;
import com.agrigov.dto.SchemeApplicationResponse;

public interface SchemeApplicationService {
    SchemeApplicationResponse create(SchemeApplicationRequest request);
    SchemeApplicationResponse update(Long applicationID, SchemeApplicationRequest request);
    List<SchemeApplicationResponse> getAppliedBySchemeId(Long schemeID);
    List<SchemeApplicationResponse> getAllApplications();
    SchemeApplicationResponse getById(Long applicationID);
//    String getStatus(Long applicationID);
	void deleteappl(Long applicationID);
}