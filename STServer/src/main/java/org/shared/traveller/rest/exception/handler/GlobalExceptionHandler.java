package org.shared.traveller.rest.exception.handler;

import org.shared.traveller.rest.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{

	private static final String MESSAGE = "An unknown error has occurred.";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException()
	{
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
