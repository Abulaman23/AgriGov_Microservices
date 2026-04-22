package com.agrigov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agrigov.dto.PolicySchemeRequest;
import com.agrigov.dto.PolicySchemeResponse;
import com.agrigov.service.PolicySchemeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/policy")
public class PolicySchemeController {

    private final PolicySchemeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PolicySchemeResponse create(@Valid @RequestBody PolicySchemeRequest request) {
        return service.create(request);
    }

    @GetMapping("/fetchall")
    public List<PolicySchemeResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{schemeID}")
    public PolicySchemeResponse getById(@PathVariable Long schemeID) {
        return service.getById(schemeID);
    }

    @PutMapping("/{schemeID}")
    public PolicySchemeResponse update(@PathVariable Long schemeID,
                                       @Valid @RequestBody PolicySchemeRequest request) {
        return service.update(schemeID, request);
    }

    @DeleteMapping("/{schemeID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long schemeID) {
        service.delete(schemeID);
    }
}