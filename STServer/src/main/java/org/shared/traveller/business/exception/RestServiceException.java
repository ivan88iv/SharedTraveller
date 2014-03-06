package org.shared.traveller.business.exception;

import org.springframework.http.HttpStatus;

/**
 * The class is used as a base class for all custom
 * exceptions that can occur in rest service in the application.
 * These exceptions occur in response to a client request
 * sent to the server. Therefore they contain information
 * about the server's response code sent back to the client.
 *
 * @author "Ivan Ivanov"
 *
 */
public abstract class RestServiceException extends RuntimeException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4151370554996601840L;

	private final HttpStatus statusCode;

	/**
	 * Creates a new application exception
	 *
	 * @param message the message of the exception
	 * @param cause the exception that caused the current exception to
	 * occur
	 * @param statusCode the status code which the server is to return
	 * to the client
	 */
	public RestServiceException(String message, Throwable cause,
			HttpStatus statusCode)
	{
		super(message, cause);
		this.statusCode = statusCode;
	}

	/**
	 * Creates a new application exception
	 *
	 * @param message an informative message about the causes
	 * that has produced the exception
	 * @param statusCode the status code of the server response
	 * to the client
	 */
	public RestServiceException(String message, HttpStatus statusCode)
	{
		super(message);
		this.statusCode = statusCode;
	}

	/**
	 * Returns the status code of the response that is to be
	 * sent to the client
	 *
	 * @return the status code of the response that is to be
	 * sent to the client
	 */
	public HttpStatus getStatusCode()
	{
		return statusCode;
	}

}
