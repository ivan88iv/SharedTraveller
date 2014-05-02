package org.shared.traveller.business.exception;

import java.text.MessageFormat;

/**
 * The exception is thrown when there is a problem while trying to delete a
 * resource
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class UnsuccessfulResourceDeletionException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -7683988120212055025L;

	private static final String MSG_TEMPLATE =
			"A problem occurred while trying to delete {0}.";

	/**
	 * Creates a new exception for unsuccessful deletion operations
	 * 
	 * @param inResourceItem
	 *            information about the item that was unsuccessfully deleted
	 */
	public UnsuccessfulResourceDeletionException(final String inResourceItem)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inResourceItem));
	}

	/**
	 * Creates a new exception for unsuccessful deletion operations
	 * 
	 * @param inResourceItem
	 *            information about the item that was unsuccessfully deleted
	 * @param inCause
	 *            the cause of the problem
	 */
	public UnsuccessfulResourceDeletionException(final String inResourceItem,
			final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inResourceItem), inCause);
	}
}
