package com.medeil.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeil.authservice.dto.AuthResponse;
import com.medeil.authservice.dto.LoginRequest;
import com.medeil.authservice.dto.RefreshTokenRequest;
import com.medeil.authservice.dto.RefreshTokenResponse;
import com.medeil.authservice.dto.RegisterRequest;
import com.medeil.authservice.dto.UserResponse;
import com.medeil.authservice.service.AuthService;
import com.medeil.authservice.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request){
    	
    	System.out.println("Register API Called");

        UserResponse response = authService.register(request);

        return new ResponseEntity<>(
                ApiResponse.success(
                        "User Registered Successfully",
                        response),
                HttpStatus.CREATED);

    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login Successful",
                        authService.login(request)
                ));
    }
    
    @GetMapping("/profile")
    public Authentication profile(Authentication authentication) {
        return authentication;
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Welcome Admin";
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String user() {
        return "Welcome User";
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(

                ApiResponse.success(
                        "Access Token Generated Successfully",
                        authService.refreshToken(request)
                ));
    }

}
