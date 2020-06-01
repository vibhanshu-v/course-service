package com.sample.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = { "com.sample" })
public class ExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(value = { ConstraintViolationException.class })
	public ErrorMessage handleBadRequestServerException(final Exception exception) {

		ErrorMessage errorMsg = new ErrorMessage(exception.getMessage());
		return errorMsg;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@org.springframework.web.bind.annotation.ExceptionHandler(value = { NoSuchElementException.class })
	public ErrorMessage handleException(final Exception exception) {

		ErrorMessage errorMsg = new ErrorMessage(exception.getMessage());
		return errorMsg;
	}
}
