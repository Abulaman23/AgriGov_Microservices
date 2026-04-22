package com.agrigov.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.agrigov.dto.FarmerRequest;
import com.agrigov.dto.FarmerResponse;
import com.agrigov.service.FarmerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/farmers")
@Slf4j // Lombok annotation for logger
@RequiredArgsConstructor // ✅ Lombok generates constructor for final fields
public class FarmerController {

	private final FarmerService service; // injected via constructor

	@PostMapping("/register")
	public FarmerResponse registerFarmer(@RequestBody FarmerRequest farmerRequest) {
		log.info("Registering new farmer: {}", farmerRequest.getName());
		return service.registerFarmer(farmerRequest);
	}

	@PatchMapping("/update/{id}")
	public FarmerResponse patchFarmer(@RequestBody FarmerRequest farmerRequest, @PathVariable("id") Long farmerId) {
		return service.partialUpdateFarmer(farmerRequest, farmerId);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteFarmer(@PathVariable("id") Long farmerId) {
		log.warn("Deleting farmer with ID: {}", farmerId);
		service.deleteFarmer(farmerId);
		return "Farmer with ID " + farmerId + " deleted successfully";
	}

	@GetMapping("/fetch/{id}")
	public FarmerResponse getFarmerById(@PathVariable("id") Long farmerId) {
		log.debug("Fetching farmer by ID: {}", farmerId);
		return service.getFarmerById(farmerId);
	}

	@GetMapping("/fetchAll")
	public List<FarmerResponse> getAllFarmers() {
		log.debug("Fetching all farmers");
		return service.getAllFarmers();
	}

	@GetMapping("/fetchByStatus/{status}")
	public List<FarmerResponse> getFarmersByStatus(@PathVariable("status") String status) {
		log.info("Fetching farmers by status: {}", status);
		return service.getFarmersByStatus(status);
	}
}
