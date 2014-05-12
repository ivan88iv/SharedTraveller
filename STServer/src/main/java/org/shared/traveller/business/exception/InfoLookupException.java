package org.shared.traveller.business.exception;

import java.text.MessageFormat;

/**
 * The class represents exceptions thrown in result to problems that occurred in
 * the search process for some requested information
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class InfoLookupException extends BusinessException
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8293464197867717026L;

	private static final String MSG_TEMPLATE =
			"A problem occurred while searching for {0} with the "
					+ "following criteria: {1}";

	private static final String NO_CRITERIA_TEMPLATE =
			"A problem occurred while searching for {0}.";

	/**
	 * Creates a new info lookup exception instance for the item being searched
	 * and the search criteria used
	 * 
	 * @param inSearchedItem
	 *            the name of the item that was searched
	 * @param inSearchCriteria
	 *            the criteria that was used for the search
	 */
	public InfoLookupException(final String inSearchedItem,
			final String inSearchCriteria)
	{

		super(MessageFormat.format(MSG_TEMPLATE, inSearchedItem,
				inSearchCriteria));
	}

	/**
	 * Creates a new info lookup exception instance for the item being searched,
	 * the search criteria used and the cause of the current exception
	 * 
	 * @param inSearchedItem
	 *            the name of the item being searched
	 * @param inSearchCriteria
	 *            the criteria used for the search
	 * @param inCause
	 *            the cause of the current exception
	 */
	public InfoLookupException(final String inSearchedItem,
			final String inSearchCriteria, final Throwable inCause)
	{
		super(MessageFormat.format(MSG_TEMPLATE, inSearchedItem,
				inSearchCriteria), inCause);
	}

	/**
	 * Creates a new lookup exception instance for the item being searched
	 * 
	 * @param inSearchedItem
	 *            the name of the item that is being searched
	 * @param inCause
	 *            the cause of the current exception
	 */
	public InfoLookupException(final String inSearchedItem,
			final Throwable inCause)
	{
		super(MessageFormat.format(NO_CRITERIA_TEMPLATE, inSearchedItem),
				inCause);
	}
}
