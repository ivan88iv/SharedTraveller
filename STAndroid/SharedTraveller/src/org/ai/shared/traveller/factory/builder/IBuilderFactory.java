package org.ai.shared.traveller.factory.builder;

import java.util.Date;

import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;

/**
 * The interface represents the functionality common for all factories of domain
 * builder instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IBuilderFactory
{
	/**
	 * Creates a new announcement builder instance
	 * 
	 * @param inFrom
	 *            the from point of the announcement. It may not be null.
	 * @param inTo
	 *            the to point of the announcement. It may not be null.
	 * @param inDepDate
	 *            the departure date of the announcement. It may not be null.
	 * @param inSeats
	 *            the number of seats for the announcement
	 * @param inDriverUsername
	 *            the driver's user name. It may not be null.
	 * @return the new builder instance
	 */
	IAnnouncement.IBuilder createAnnouncementBuilder(
			final String inFrom, final String inTo,
			final Date inDepDate, final short inSeats,
			final String inDriverUsername);

	/**
	 * The method creates a new request information builder instance
	 * 
	 * @return the newly created request information builder
	 */
	IRequestInfo.IBuilder createRequestInfoBuilder();
}
