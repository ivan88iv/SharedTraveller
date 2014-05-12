package org.shared.traveller.business.factory.builder;

import java.util.Date;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentNotification.IPersistentNotificationBuilder;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.client.domain.INotification.Type;

/**
 * The interface represents the common functionality for factory classes used
 * for creation of various domain builder instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IBusinessBuilderFactory
{
	/**
	 * The method creates a new notification builder instance
	 * 
	 * @param inType
	 *            the type of the notification to be created by the builder. It
	 *            may not be null.
	 * @param inReceiver
	 *            the receiver of the notification to be created by the builder.
	 *            It may not be null.
	 * @param inSender
	 *            the sender of the notification to be created by the builder.
	 *            It may not be null.
	 * @param inAnnouncement
	 *            the announcement for the notification that is the be created
	 *            by the builder. It may not be null.
	 * @param inCreationDate
	 *            the date when the notification is to be created. It may not be
	 *            null.
	 * @return the builder instance used to create a new notification.
	 * 
	 * @throws IncorrectDomainTypeException
	 *             when the traveller instance is not from a correct domain
	 */
	IPersistentNotificationBuilder createNotificationsBuilder(
			final Type inType,
			final IPersistentTraveller inReceiver,
			final IPersistentTraveller inSender,
			final IPersistentAnnouncement inAnnouncement,
			final Date inCreationDate);
}
