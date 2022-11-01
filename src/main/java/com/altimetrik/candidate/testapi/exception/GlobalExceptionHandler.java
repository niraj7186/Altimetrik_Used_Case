package com.altimetrik.candidate.testapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global Exception class to handle Internal Exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleInternalServerException(Exception ex, WebRequest web)
	{
		ErrorObject error = new ErrorObject();
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
