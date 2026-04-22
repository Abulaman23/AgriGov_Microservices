package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.SchemeRequestDTO;

@FeignClient(name = "SchemeService")
public interface SchemeClient {

	@GetMapping("/application/{schemeId}/{farmerId}/fetch")
	SchemeRequestDTO getApplication(@PathVariable Long schemeId, @PathVariable Long farmerId);
}
