package org.shared.traveller.business.exception;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException
{

	private static final long serialVersionUID = 1;

	private HttpStatus statusCode;

	public ApplicationException(String message, Throwable cause, HttpStatus statusCode)
	{
		super(message, cause);
		this.statusCode = statusCode;
	}

	public ApplicationException(String message, HttpStatus statusCode)
	{
		super(message);
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode()
	{
		return statusCode;
	}

}
