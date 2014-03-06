package org.shared.traveller.business.exception.persistence;

/**
 * The class represents persistent exceptions that are thrown
 * when there is a problem while updating data
 * in the persistent layer.
 *
 * @author "Ivan Ivanov"
 *
 */
public class DataModificationException extends PersistentException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3358837003459055085L;

	/**
	 * Creates a new data modification exception
	 *
	 * @param inMessage a short description of the
	 * cause of the exception
	 * @param cause the exception that caused the current exception to
	 * occur
	 */
	public DataModificationException(String inMessage, Throwable cause)
	{
		super(inMessage, cause);
	}

	/**
	 * Creates a new data modification exception
	 *
	 * @param inMessage a short description of the
	 * cause of the exception
	 */
	public DataModificationException(String inMessage)
	{
		super(inMessage);
	}
}
