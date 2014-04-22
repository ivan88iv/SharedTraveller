package org.shared.traveller.business.exception;

/**
 * The class represents an exception which is thrown when the requested
 * update operation cannot be executed because of data inconsistencies
 *
 * @author "Ivan Ivanov"
 *
 */
public class IllegalUpdateOperationException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -7802256923832930838L;

	/**
	 * Creates an illegal update operation exception
	 *
	 * @param inMessage the descriptive message for the exception
	 */
	public IllegalUpdateOperationException(final String inMessage)
	{
		super(inMessage);
	}

	/**
	 * Creates a new illegal update operation exception
	 *
	 * @param inMessage the descriptive message for the exception
	 * @param inCause the cause of the problem
	 */
	public IllegalUpdateOperationException(final String inMessage,
			final Throwable inCause)
	{
		super(inMessage, inCause);
	}
}
