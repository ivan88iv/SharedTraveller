package org.shared.traveller.business.exception.persistence;

/**
 * The class represents persistent exceptions that are thrown as
 * a result from a problem which has occurred while extracting
 * data from the persistent layer.
 *
 * @author "Ivan Ivanov"
 *
 */
public class DataExtractionException extends PersistentException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3358837003459055085L;

	/**
	 * Creates a new data extraction exception
	 *
	 * @param inMessage a short description of the
	 * cause of the exception
	 * @param cause the exception that caused the current exception to
	 * occur
	 */
	public DataExtractionException(String inMessage, Throwable cause)
	{
		super(inMessage, cause);
	}
}
