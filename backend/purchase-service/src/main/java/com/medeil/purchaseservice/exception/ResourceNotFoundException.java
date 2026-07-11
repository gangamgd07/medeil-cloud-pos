package com.medeil.purchaseservice.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
