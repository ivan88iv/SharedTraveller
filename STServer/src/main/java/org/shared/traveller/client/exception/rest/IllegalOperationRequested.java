package org.shared.traveller.client.exception.rest;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

/**
 * The exception is thrown when the request operation is illegal with regard to
 * server data. This exception is associated with
 * {@link HttpStatus#INTERNAL_SERVER_ERROR} status code
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class IllegalOperationRequested extends RestServiceException
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 643437579233386509L;

	private static final String MSG_TEMPLATE =
			"The {0} operation is not legal.";

	/**
	 * Creates a new illegal operation requested exception
	 * 
	 * @param inOperationName
	 *            the name of the illegal operation
	 */
	public IllegalOperationRequested(final String inOperationName)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inOperationName),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Creates a new illegal operation requested exception
	 * 
	 * @param inOperationName
	 *            the name of the illegal operation
	 * @param inCause
	 *            the cause of the exception
	 */
	public IllegalOperationRequested(final String inOperationName,
			final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inOperationName), inCause,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
