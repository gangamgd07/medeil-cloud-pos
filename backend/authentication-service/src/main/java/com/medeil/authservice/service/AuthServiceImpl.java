package com.medeil.authservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medeil.authservice.dto.AuthResponse;
import com.medeil.authservice.dto.LoginRequest;
import com.medeil.authservice.dto.RefreshTokenRequest;
import com.medeil.authservice.dto.RefreshTokenResponse;
import com.medeil.authservice.dto.RegisterRequest;
import com.medeil.authservice.dto.UserResponse;
import com.medeil.authservice.entity.User;
import com.medeil.authservice.exception.DuplicateResourceException;
import com.medeil.authservice.jwt.JwtUtil;
import com.medeil.authservice.mapper.UserMapper;
import com.medeil.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(RegisterRequest request) {

        if(userRepository.existsByUsername(request.getUsername())) {

            throw new DuplicateResourceException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        User user = mapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);

        return mapper.toResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Username or Password"));

        if (!passwordEncoder.matches(request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Username or Password");
        }

        String accessToken = jwtUtil.generateAccessToken(
                user.getId(),
                user.getUsername(),
                user.getRole().name());

        String refreshToken = jwtUtil.generateRefreshToken(
                user.getId(),
                user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtUtil.validateRefreshToken(refreshToken, username)) {
            throw new RuntimeException("Refresh Token Expired");
        }

        String newAccessToken =
                jwtUtil.generateAccessToken(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().name());

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
