package org.shared.traveller.rest.exception.handler;

import org.shared.traveller.client.exception.rest.RestServiceException;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestServiceExceptionHandler
{
	@ExceptionHandler(value = RestServiceException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(RestServiceException e)
	{
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), e.getStatusCode());
	}
}
