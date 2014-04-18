package org.shared.traveller.business.exception;


/**
 * The class is used to represent the functionality common
 * for business layer exceptions
 *
 * @author "Ivan Ivanov"
 *
 */
public class BusinessException extends RuntimeException
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 6749774009958382199L;

	/**
	 * Creates a new business exception
	 *
	 * @param message the message of the exception
	 * @param cause the exception that caused the current exception to
	 * occur
	 */
	public BusinessException(final String message,
			final Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates a new business tier exception
	 *
	 * @param message an informative message about the causes
	 * that has produced the exception
	 */
	public BusinessException(final String message)
	{
		super(message);
	}
}
