package com.agrigov.service;

import java.util.List;

import com.agrigov.dto.AuthRequest;
import com.agrigov.dto.AuthResponse;
import com.agrigov.dto.RefreshTokenRequest;
import com.agrigov.dto.RegisterRequest;
import com.agrigov.dto.UserRequest;
import com.agrigov.dto.UserResponse;

public interface AuthService {

	AuthResponse login(AuthRequest request);

	AuthResponse refresh(RefreshTokenRequest request);

	void logout(String refreshToken);

	UserResponse  register(RegisterRequest request);

	void changePassword( String newPassword);

	void deleteUser(Long id);

	UserResponse getUserById(Long id);

	List<UserResponse> getAllUsers();

	UserResponse updateUser(Long id, UserRequest request);


}