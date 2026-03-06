package com.eCommerce.GovindasStore.Exception;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Catch Custom RuntimeExceptions (Stock, Not Found, etc.)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
		
		ErrorResponse error =new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	// Catch Database/SQL Errors (Like duplicate emails)
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(SQLException ex){
		
		ErrorResponse error =new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: "+ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
}
