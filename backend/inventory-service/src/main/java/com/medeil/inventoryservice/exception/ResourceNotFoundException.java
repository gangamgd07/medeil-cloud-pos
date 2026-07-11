package com.medeil.inventoryservice.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
