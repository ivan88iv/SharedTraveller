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
	 * which the user with the specified id is the receiver
	 * 
	 * @param inUserId
	 *            the id of the notifications' receiver. It may not be null
	 * @return a list of the notifications for which the user is the receiver
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while extracting the user notifications
	 */
	List<? extends IPersistentNotification> extractUserNotifications(
			final Long inUserId);

	/**
	 * The method removes all specified notifications from the persistent layer
	 * 
	 * @param inNotifications
	 *            the notifications to be removed. It may not be null
	 * 
	 * @throws DataModificationException
	 *             if a problem occurs in the removala process
	 */
	void remove(final List<? extends IPersistentNotification> inNotifications);
}
