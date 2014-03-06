package org.shared.traveller.business.exception.persistence;


/**
 * The class represents an exception thrown when
 * a problem occurs while working with the persistent
 * layer of the application
 *
 * @author "Ivan Ivanov"
 *
 */
public class PersistentException extends RuntimeException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3643316894209447368L;

	/**
	 * Creates a new persistent exception
	 *
	 * @param message the message of the exception
	 * @param cause the exception that caused the current exception to
	 * occur
	 */
	public PersistentException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates a new persistent exception
	 *
	 * @param message an informative message about the causes
	 * that has produced the exception
	 */
	public PersistentException(String message)
	{
		super(message);
	}
}
