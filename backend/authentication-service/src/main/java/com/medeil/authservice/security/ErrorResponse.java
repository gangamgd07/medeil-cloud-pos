package com.medeil.authservice.security;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private boolean success;

    private int status;

    private String message;

    private LocalDateTime timestamp;

}