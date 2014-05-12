package org.shared.traveller.business.domain;

import java.util.Date;

import org.shared.traveller.business.domain.visitor.IPersistentNotificationVisitor;
import org.shared.traveller.client.domain.INotification.Type;

/**
 * The interface represents the common functionality for concrete persistent
 * notification instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IPersistentNotification extends IPersistent
{
	/**
	 * The interface represents the functionality a persistent notification
	 * builder should have
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static interface IPersistentNotificationBuilder
	{
		/**
		 * The method sets the id of the new notification
		 * 
		 * @param inId
		 *            the new id value
		 * @return the builder instance
		 */
		IPersistentNotificationBuilder id(final Long inId);

		/**
		 * Builds the persistent notification instance
		 * 
		 * @return the newly built instance
		 */
		IPersistentNotification build();
	}

	/**
	 * Returns the date this notification was created
	 * 
	 * @return the date this notification was created
	 */
	Date getCreationDate();

	/**
	 * Returns the type of the notification
	 * 
	 * @return the type of the notification
	 */
	Type getType();

	/**
	 * Returns the receiver of the notification
	 * 
	 * @return the receiver of the notification
	 */
	IPersistentTraveller getReceiver();

	/**
	 * Returns the sender of the notification
	 * 
	 * @return the sender of the notification
	 */
	IPersistentTraveller getSender();

	/**
	 * Returns the announcement for which the current notification has been sent
	 * 
	 * @return the announcement for which the current notification has been sent
	 */
	IPersistentAnnouncement getAnnouncement();

	/**
	 * The method is used in order to add some new functionality to the
	 * notification by means of the Visitor pattern
	 * 
	 * @param inVisitor
	 *            the visitor that adds some new functionality to the current
	 *            notification instance. It may not be null
	 */
	void accept(final IPersistentNotificationVisitor inVisitor);
}
