package org.shared.traveller.business.exception;

import org.springframework.http.HttpStatus;

public class IncorrectInputException extends RestServiceException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -1845110006128156565L;

	public IncorrectInputException(String message, Throwable cause,
			HttpStatus statusCode)
	{
		super(message, cause, statusCode);
	}

	public IncorrectInputException(String message, HttpStatus statusCode)
	{
		super(message, statusCode);
	}
}
