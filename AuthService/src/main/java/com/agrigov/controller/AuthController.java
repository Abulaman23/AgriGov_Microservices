package com.agrigov.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.agrigov.dto.AuthRequest;
import com.agrigov.dto.AuthResponse;
import com.agrigov.dto.ChangePasswordRequest;
import com.agrigov.dto.RefreshTokenRequest;
import com.agrigov.dto.RegisterRequest;
import com.agrigov.dto.UserRequest;
import com.agrigov.dto.UserResponse;
import com.agrigov.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ---------------- AUTH APIs ----------------

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("X-REFRESH-TOKEN") String refreshToken) {
        authService.logout(refreshToken);
    }

    /**
     * Change password for the currently authenticated user.
     * User identity is derived from JWT (SecurityContext),
     * NOT from headers.
     */
    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        authService.changePassword(request.getNewPassword());
    }

    // ---------------- USER APIs ----------------

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return authService.getUserById(id);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return authService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        return authService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        authService.deleteUser(id);
    }
}
