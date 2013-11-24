package org.shared.traveller.rest.exception.handler;

import org.shared.traveller.business.exception.ApplicationException;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler
{

	@ExceptionHandler(value = ApplicationException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e)
	{
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), e.getStatusCode());
	}
}
