package org.ai.shared.traveller.client.factory;

import java.util.Date;

import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.INotification.Type;

/**
 * The interface represents the common functionality a factory for creating
 * simple client domain instances must possess
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IDomainFactory
{
	/**
	 * The method creates a new notification instance
	 * 
	 * @param inType
	 *            the type of the notification. It may not be null.
	 * @param inCreationDate
	 *            the date the notification was created. It may not be null.
	 * @param inDescription
	 *            the description of the notification
	 * @return the newly created notification
	 */
	INotification createNotification(final Type inType,
			final Date inCreationDate, final String inDescription);
}
