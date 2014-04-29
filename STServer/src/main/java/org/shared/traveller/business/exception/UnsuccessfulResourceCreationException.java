package org.shared.traveller.business.exception;

import java.text.MessageFormat;

/**
 * This exception illustrates problems in a resource creation process
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class UnsuccessfulResourceCreationException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3846354305052665891L;

	private static final String MSG_TEMPLATE =
			"A problem occurred while trying to create {0}.";

	/**
	 * Creates a new exception for unsuccessful creation operations
	 * 
	 * @param inResourceItem
	 *            information about the item that was unsuccessfully created
	 */
	public UnsuccessfulResourceCreationException(final String inResourceItem)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inResourceItem));
	}

	/**
	 * Creates a new exception for unsuccessful creation operations
	 * 
	 * @param inResourceItem
	 *            information about the item that was unsuccessfully updated
	 * @param inCause
	 *            the cause of the problem
	 */
	public UnsuccessfulResourceCreationException(final String inResourceItem,
			final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inResourceItem), inCause);
	}
}
