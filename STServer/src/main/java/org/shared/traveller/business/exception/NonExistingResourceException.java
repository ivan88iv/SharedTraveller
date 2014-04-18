package org.shared.traveller.business.exception;

import java.text.MessageFormat;

/**
 * The class represents exception which is thrown when
 * the desired resource was not found because it does
 * not exist
 *
 * @author "Ivan Ivanov"
 *
 */
public class NonExistingResourceException extends BusinessException
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -6366016130796421779L;

	private static final String MSG_TEMPLATE =
			"The resource {0} could not be found.";

	/**
	 * Creates a new non existing resource exception
	 *
	 * @param inItem the name of the item which does not exist
	 */
	public NonExistingResourceException(final String inItem)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inItem));
	}

	/**
	 * Creates a new non existing resource exception
	 *
	 * @param inItem the name of the item which does not exist
	 * @param inCause the cause of the problem
	 */
	public NonExistingResourceException(final String inItem,
			final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inItem), inCause);
	}
}
