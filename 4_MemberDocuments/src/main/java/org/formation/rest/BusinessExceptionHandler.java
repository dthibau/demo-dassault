package org.formation.rest;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.formation.rest.views.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDto> handleEntityNotFoundException(HttpServletRequest request, Throwable ex) {
		
		ErrorDto error = ErrorDto.builder().level("ERROR").mesage(ex.getLocalizedMessage()).timestamp(new Date()).build();
		return new ResponseEntity<ErrorDto>(
		          error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDto error = ErrorDto.builder().level("ERROR").mesage(ex.getLocalizedMessage()).timestamp(new Date()).build();
		return new ResponseEntity<Object>(
		          error, HttpStatus.BAD_REQUEST);
	}
	
	
}
