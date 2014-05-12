package org.shared.traveller.business.exception;

/**
 * The class represents an exception which is thrown when the requested
 * operation cannot be executed because of data inconsistencies
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class IllegalOperationException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -7802256923832930838L;

	/**
	 * Creates an illegal operation exception
	 * 
	 * @param inMessage
	 *            the descriptive message for the exception
	 */
	public IllegalOperationException(final String inMessage)
	{
		super(inMessage);
	}

	/**
	 * Creates a new illegal operation exception
	 * 
	 * @param inMessage
	 *            the descriptive message for the exception
	 * @param inCause
	 *            the cause of the problem
	 */
	public IllegalOperationException(final String inMessage,
			final Throwable inCause)
	{
		super(inMessage, inCause);
	}
}
