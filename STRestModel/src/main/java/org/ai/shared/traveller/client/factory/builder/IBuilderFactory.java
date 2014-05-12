package org.ai.shared.traveller.client.factory.builder;

import java.util.Date;

import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;

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
	 * The method creates a new builder instance for creating requested
	 * announcements
	 * 
	 * @return the builder instance that is created
	 */
	IRequestedAnnouncement.IBuilder createRequestedAnnouncementBuilder();

	/**
	 * The method creates a new request information builder instance
	 * 
	 * @return the newly created request information builder
	 */
	IRequestInfo.IBuilder createRequestInfoBuilder();

	/**
	 * The method creates a new notification builder instance
	 * 
	 * @return the newly created notification builder instance
	 */
	INotification.IBuilder createNotificationBuilder();

	/**
	 * Creates a new builder instance for instantiating notification travellers
	 * 
	 * @return a new builder instance for instantiating notification travellers
	 */
	INotificationTraveller.IBuilder createNotificationTravellerBuilder();
}
