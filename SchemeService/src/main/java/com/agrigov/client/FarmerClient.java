package com.agrigov.client;

import com.agrigov.dto.FarmerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
@FeignClient(name = "FARMERSERVICE") // Ensure this matches the name in Eureka
public interface FarmerClient {
 
    @GetMapping("/farmers/fetch/{id}")
    FarmerResponse getFarmerById(@PathVariable("id") Long farmerId);
}
 
