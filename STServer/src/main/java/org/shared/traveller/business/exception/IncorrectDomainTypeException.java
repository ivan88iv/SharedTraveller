package org.shared.traveller.business.exception;

/**
 * The class represents an exception that is thrown when a domain
 * instance of an incorrect type is provided
 *
 * @author "Ivan Ivanov"
 *
 */
public class IncorrectDomainTypeException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1911096688751537290L;

	/**
	 * Creates an incorrect domain type exception
	 *
	 * @param inMessage the descriptive message for the exception
	 */
	public IncorrectDomainTypeException(final String inMessage)
	{
		super(inMessage);
	}

	/**
	 * Creates an incorrect domain type exception
	 *
	 * @param inMessage the descriptive message for the exception
	 * @param inCause the cause of the problem
	 */
	public IncorrectDomainTypeException(final String inMessage,
			final Throwable inCause)
	{
		super(inMessage, inCause);
	}
}
