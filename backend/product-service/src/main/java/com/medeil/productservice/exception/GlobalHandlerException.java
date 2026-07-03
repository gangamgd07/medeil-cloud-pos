package com.medeil.productservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {

	        Map<String, Object> response = new HashMap<>();

	        response.put("timestamp", LocalDateTime.now());
	        response.put("status", HttpStatus.NOT_FOUND.value());
	        response.put("message", ex.getMessage());

	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

	        Map<String, String> errors = new HashMap<>();

	        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(DuplicateResourceException.class)
	    public ResponseEntity<Object> handleDuplicate(
	            DuplicateResourceException ex){

	        Map<String,Object> response = new HashMap<>();

	        response.put("timestamp", LocalDateTime.now());

	        response.put("status",409);

	        response.put("message",ex.getMessage());

	        return new ResponseEntity<>(response,HttpStatus.CONFLICT);

	    }

}
