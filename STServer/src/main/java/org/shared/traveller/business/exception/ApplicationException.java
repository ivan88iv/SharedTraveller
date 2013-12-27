package org.shared.traveller.business.exception;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4151370554996601840L;

	private final HttpStatus statusCode;

	public ApplicationException(String message, Throwable cause,
			HttpStatus statusCode)
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
