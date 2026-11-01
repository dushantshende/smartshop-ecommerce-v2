package com.ds.smartshop.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleProductNotFoundException(ProductNotFoundException e	){
		
		List<String> errors=List.of(e.getMessage());
		
		ApiErrorResponse response=new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),"Product Not Found", errors);
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
		
		List<String> errors=ex.getBindingResult().getFieldErrors().stream().map(error->error.getDefaultMessage()).collect(Collectors.toList());
		
		ApiErrorResponse response=new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),"Validation failed",errors);
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
