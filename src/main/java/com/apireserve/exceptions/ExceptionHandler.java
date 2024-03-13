package com.apireserve.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
		String error = "Objeto não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		String path = request.getRequestURI();
		var standardError = new StandardError(Instant.now(), status.value(), error, exception.getMessage(), path);
		return ResponseEntity.status(status).body(standardError);
	}
	
}
