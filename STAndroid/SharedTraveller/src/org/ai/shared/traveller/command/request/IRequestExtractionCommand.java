package org.ai.shared.traveller.command.request;

import org.ai.shared.traveller.request.RequestsAdapter;

/**
 * The interface should be implemented by classes which extract requests from
 * the server side
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestExtractionCommand
{
	/**
	 * The method extracts requests for the specified announcement and applies
	 * them to the adapter provided
	 * 
	 * @param inAnnouncementId
	 *            the announcement id which designates the announcement for
	 *            which requests are extracted. It may not be null
	 * @param inAdapter
	 *            the adapter to which the extracted requests are provided. It
	 *            may not be null
	 */
	void extractRequests(final Long inAnnouncementId,
			final RequestsAdapter inAdapter);
}
