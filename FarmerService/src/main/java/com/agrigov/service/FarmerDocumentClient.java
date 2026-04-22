package com.agrigov.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.FarmerDocumentResponse;

@FeignClient(name = "FarmerDocumentService")
public interface FarmerDocumentClient {

    @GetMapping("/documents/farmer/{farmerId}")
    List<FarmerDocumentResponse> getDocumentsByFarmerId(@PathVariable("farmerId") Long farmerId);
}