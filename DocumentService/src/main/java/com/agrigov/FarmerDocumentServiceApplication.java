package com.agrigov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FarmerDocumentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmerDocumentServiceApplication.class, args);
    }
}