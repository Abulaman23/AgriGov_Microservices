package com.agrigov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrigov.dto.UserDTO;

 
@FeignClient(name = "user-service")
public interface UserClient {

 
    @GetMapping("/api/users/getUserById/{id}")
    UserDTO getUser(@PathVariable Long id);
}