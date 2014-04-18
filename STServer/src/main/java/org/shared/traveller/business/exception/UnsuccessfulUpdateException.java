package org.shared.traveller.business.exception;

import java.text.MessageFormat;

/**
 * The class represents exception that is thrown when the update
 * operation performed was not successful
 *
 * @author "Ivan Ivanov"
 *
 */
public class UnsuccessfulUpdateException extends BusinessException
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3846354305052665891L;

	private static final String MSG_TEMPLATE =
			"A problem occurred while trying to update {0}.";

	/**
	 * Creates a new exception for unsuccessful update operations
	 *
	 * @param inItem the name of the item that was unsuccessfully updated
	 */
	public UnsuccessfulUpdateException(final String inItem)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inItem));
	}

	/**
	 * Creates a new exception for unsuccessful update operations
	 *
	 * @param inItem the name of the item that was unsuccessfully updated
	 * @param inCause the cause of the problem
	 */
	public UnsuccessfulUpdateException(final String inItem,
			final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inItem), inCause);
	}
}
