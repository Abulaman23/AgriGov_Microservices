package com.agrigov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SchemeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchemeServiceApplication.class, args);
	}

}
