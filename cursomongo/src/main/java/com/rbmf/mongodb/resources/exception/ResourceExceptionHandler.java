package com.rbmf.mongodb.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rbmf.mongodb.services.exception.ObjectNotFoundException;

@ControllerAdvice		// indica que essa classe é responsável por tratar possíveis erros
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)		// padrão do framework
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		// System.currentTimeMillis() pega o instante do erro
		
		return ResponseEntity.status(status).body(err);
	}
	
}
