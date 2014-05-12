package org.shared.traveller.business.dao;

import java.util.List;

import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;

/**
 * The interface represents the common functionality for DAOs responsible for
 * access operations regarding notification instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface INotificationDAO extends IDAO<IPersistentNotification>
{
	/**
	 * The method extracts and returns the persistent notification instances for
	 * which the user with the specified id is the receiver. The notifications
	 * are returned in order from the oldest to the newest one.
	 * 
	 * @param inUserId
	 *            the id of the notifications' receiver. It may not be null
	 * @return a list of the notifications for which the user is the receiver.
	 *         The notifications are returned in order from the newest to the
	 *         oldest one.
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while extracting the user notifications
	 */
	List<? extends IPersistentNotification> extractUserNotifications(
			final Long inUserId);

	/**
	 * The method removes all notifications for the specified driver and
	 * announcement from the persistent layer
	 * 
	 * @param inDriverId
	 *            the id of the driver for who notifications are removed. It may
	 *            not be null
	 * @param inAnnouncementId
	 *            the id of the announcement for which notifications are
	 *            removed. It may not be null
	 * 
	 * @throws DataModificationException
	 *             if a problem occurs in the removal process
	 */
	void removeDriverNotifications(final Long inDriverId,
			final Long inAnnouncementId);

	/**
	 * The method removes all notifications for the specified passenger from the
	 * persistent layer
	 * 
	 * @param inPassengerId
	 *            the id of the passenger for who notifications are removed. It
	 *            may not be null
	 * 
	 * @throws DataModificationException
	 *             if a problem occurs in the removal process
	 */
	void removePassengerNotifications(final Long inPassengerId);
}
