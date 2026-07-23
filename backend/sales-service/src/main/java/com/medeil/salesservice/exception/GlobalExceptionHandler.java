package com.medeil.salesservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

	  @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Object> handleValidation(
	            MethodArgumentNotValidException ex) {


	        Map<String,String> errors = new HashMap<>();

	        ex.getBindingResult()
	                .getFieldErrors()
	                .forEach(error ->
	                        errors.put(
	                                error.getField(),
	                                error.getDefaultMessage()
	                        )
	                );


	        return ResponseEntity
	                .badRequest()
	                .body(errors);
	    }

	    
	    @ExceptionHandler(DuplicateResourceException.class)
	    public ResponseEntity<ErrorResponse> handleDuplicate(
	            DuplicateResourceException ex) {

	        return buildResponse(
	                HttpStatus.CONFLICT,
	                ex.getMessage()
	        );
	    }
	    
	    @ExceptionHandler(InsufficientStockException.class)
	    public ResponseEntity<ErrorResponse> handleInsufficientStock(
	            InsufficientStockException ex) {

	        return buildResponse(
	                HttpStatus.BAD_REQUEST,
	                ex.getMessage()
	        );
	    }
	    
	    @ExceptionHandler(InvalidSaleStateException.class)
	    public ResponseEntity<ErrorResponse> handleInvalidSaleState(
	            InvalidSaleStateException ex) {

	        return buildResponse(
	                HttpStatus.CONFLICT,
	                ex.getMessage()
	        );
	    }
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGenericException(
	            Exception ex) {


	        return buildResponse(
	                HttpStatus.INTERNAL_SERVER_ERROR,
	                ex.getMessage()
	        );
	    }
	    
	    private ResponseEntity<ErrorResponse> buildResponse(
	            HttpStatus status,
	            String message) {


	        ErrorResponse response =
	                new ErrorResponse(
	                        LocalDateTime.now(),
	                        status.value(),
	                        status.getReasonPhrase(),
	                        message
	                );


	        return new ResponseEntity<>(
	                response,
	                status
	        );
	    }

}
