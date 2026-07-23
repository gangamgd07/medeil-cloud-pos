package com.medeil.salesservice.exception;

public class InsufficientStockException extends RuntimeException{

	public InsufficientStockException(String message) {
		super(message);
	}
}
