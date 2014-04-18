package org.shared.traveller.business.exception;

import org.springframework.http.HttpStatus;

/**
 * The exception is thrown when an incorrect input is provided to
 * a REST service
 *
 * @author "Ivan Ivanov"
 *
 */
public class IncorrectInputException extends RestServiceException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -1845110006128156565L;

	/**
	 * Creates a new incorrect input exception
	 *
	 * @param message the message of the exception
	 * @param cause the cause of the exception
	 * @param statusCode the status code for this exception
	 */
	public IncorrectInputException(String message, Throwable cause,
			HttpStatus statusCode)
	{
		super(message, cause, statusCode);
	}

	/**
	 * Creates a new incorrect input exception
	 *
	 * @param message the message of the exception
	 * @param statusCode the status code for theis exception
	 */
	public IncorrectInputException(String message, HttpStatus statusCode)
	{
		super(message, statusCode);
	}
}
