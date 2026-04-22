package com.agrigov.service;

import java.util.List;
import com.agrigov.dto.FarmerRequest;
import com.agrigov.dto.FarmerResponse;

public interface FarmerService {

    FarmerResponse registerFarmer(FarmerRequest farmerRequest);

    // ✅ Partial update using PATCH (status excluded)
    FarmerResponse partialUpdateFarmer(FarmerRequest farmerRequest, Long farmerId);

    void deleteFarmer(Long farmerId);

    FarmerResponse getFarmerById(Long farmerId);

    List<FarmerResponse> getAllFarmers();

    List<FarmerResponse> getFarmersByStatus(String status);
}