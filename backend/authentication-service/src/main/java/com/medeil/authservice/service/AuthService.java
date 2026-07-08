package com.medeil.authservice.service;

import com.medeil.authservice.dto.AuthResponse;
import com.medeil.authservice.dto.LoginRequest;
import com.medeil.authservice.dto.RefreshTokenRequest;
import com.medeil.authservice.dto.RefreshTokenResponse;
import com.medeil.authservice.dto.RegisterRequest;
import com.medeil.authservice.dto.UserResponse;

public interface AuthService {
	
	UserResponse register(RegisterRequest request);
	
	AuthResponse login(LoginRequest request);
	
	RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
