package org.shared.traveller.client.exception.rest;

import org.springframework.http.HttpStatus;

/**
 * The class represents business exceptions that occur within the application
 * and which reason is not entirely due to a user's action. The response code is
 * {@link HttpStatus#INTERNAL_SERVER_ERROR}
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class InternalBusinessException extends RestServiceException
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -1541226127127729107L;

	/**
	 * Creates a new internal business exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the exception that caused the current exception to occur
	 */
	public InternalBusinessException(String message, Throwable cause)
	{
		super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Creates a new internal business exception
	 * 
	 * @param message
	 *            an informative message about the causes that has produced the
	 *            exception
	 */
	public InternalBusinessException(String message)
	{
		super(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
