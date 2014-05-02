package org.shared.traveller.rest.domain;

import java.io.Serializable;

/**
 * The class contains an error response from the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class ErrorResponse implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -515764975675348074L;

	private String message;

	/**
	 * Constructs an empty error response
	 * 
	 */
	public ErrorResponse()
	{
		super();
	}

	/**
	 * Constructs an error response with a specific error message
	 * 
	 * @param message
	 *            the error message describing the server error
	 */
	public ErrorResponse(final String message)
	{
		super();
		this.message = message;
	}

	/**
	 * Returns the error message for the current response
	 * 
	 * @return the error message for the current response
	 */
	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();

		builder.append("----------- Error Response ---------------\n");
		builder.append("message: " + message + "\n");
		builder.append("------------------------------------------\n");
		return builder.toString();
	}
}
