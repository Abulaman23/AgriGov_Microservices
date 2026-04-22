package com.agrigov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient // Add this!
public class FarmerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmerServiceApplication.class, args);
	}

}
