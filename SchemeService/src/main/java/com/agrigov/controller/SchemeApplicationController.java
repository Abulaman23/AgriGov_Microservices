package com.agrigov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.agrigov.dto.SchemeApplicationRequest;
import com.agrigov.dto.SchemeApplicationResponse;
import com.agrigov.service.SchemeApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/application")
public class SchemeApplicationController {

    private final SchemeApplicationService service;

    public SchemeApplicationController(SchemeApplicationService service) {
        this.service = service;
    }

    // ---------------- CREATE ----------------

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public SchemeApplicationResponse create(
            @Valid @RequestBody SchemeApplicationRequest request) {
        return service.create(request);
    }
    
   // ---------------- GET ALL APPLICATIONS ----------------

    @GetMapping("/all")
    public List<SchemeApplicationResponse> getAllApplications() {
        return service.getAllApplications();
    }

    // ---------------- GET ALL BY SCHEME ----------------

    @GetMapping("/scheme/{schemeID}")
    public List<SchemeApplicationResponse> getAppliedBySchemeId(
            @PathVariable Long schemeID) {
        return service.getAppliedBySchemeId(schemeID);
    }

    // ---------------- GET BY APPLICATION ID ----------------

    @GetMapping("/{applicationID}")
    public SchemeApplicationResponse getById(
            @PathVariable Long applicationID) {
        return service.getById(applicationID);
    }

    // ---------------- ✅ UPDATE APPLICATION ----------------

    @PutMapping("/{applicationID}")
    public SchemeApplicationResponse update(
            @PathVariable Long applicationID,
            @Valid @RequestBody SchemeApplicationRequest request) {

        return service.update(applicationID, request);
    }

    // ---------------- DELETE ----------------

    @DeleteMapping("/{applicationID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteappl(@PathVariable Long applicationID) {
        service.deleteappl(applicationID);
    }
}