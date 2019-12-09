package com.rbmf.spring.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rbmf.spring.services.exceptions.ResourceNotFoundException;

@ControllerAdvice		// vai interceptar as excessões que aparecerem para que possa ser tratadas de forma personalizada
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)		// esse método vai interceptar qualquer excessão desse tipo que foi lançada e fazer o tratamento
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
