package org.shared.traveller.client.domain;

import java.util.Date;

import org.shared.traveller.client.domain.visitor.INotificationVisitor;

/**
 * The interface represents a server side notification
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface INotification
{
	/**
	 * The enumeration represents the different possible notification types
	 * available
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public enum Type
	{
		/**
		 * A new request notification
		 */
		NEW_REQUEST,

		/**
		 * Notification for a rejection of a travel request
		 */
		REQUEST_REJECTION,

		/**
		 * Notification for an acceptance of a travel request
		 */
		REQUEST_ACCEPTANCE,

		/**
		 * Notification for a declination of a travel request by the client that
		 * has made the request
		 */
		REQUEST_DECLINATION,

		/**
		 * Notification for cancelation of the travel
		 */
		TRIP_CANCELLATION;
	}

	/**
	 * The interface represents the common functionality a notification builder
	 * should possess
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static interface IBuilder
	{
		/**
		 * The method sets a new creation date for the built notification
		 * 
		 * @param inDate
		 *            the new creation date
		 * @return the builder instance
		 */
		IBuilder creationDate(final Date inDate);

		/**
		 * The method sets a new title for the notification to be built
		 * 
		 * @param inTitle
		 *            the new title
		 * @return the builder instance
		 */
		IBuilder title(final String inTitle);

		/**
		 * The method sets a new description for the notification that is built
		 * 
		 * @param inDescription
		 *            the new description
		 * @return the builder instance
		 */
		IBuilder description(final String inDescription);

		/**
		 * The method sets a new type for the notification to be built
		 * 
		 * @param inType
		 *            the notification's type
		 * @return the builder instance
		 */
		IBuilder type(final Type inType);

		/**
		 * The method sets a new announcement id for the notification to be
		 * built
		 * 
		 * @param inId
		 *            the announcement id of the notification
		 * @return the builder instance
		 */
		IBuilder announcementId(final Long inId);

		/**
		 * The method builds a new notification instance
		 * 
		 * @return the newly built instance
		 */
		INotification build();
	}

	/**
	 * Returns the date the notification was created on
	 * 
	 * @return the date the notification was created on
	 */
	Date getCreationDate();

	/**
	 * Returns the title for the current notification
	 * 
	 * @return the title for the current notification
	 */
	String getTitle();

	/**
	 * Returns the textual description of the notification
	 * 
	 * @return the textual description of the notification
	 */
	String getDescription();

	/**
	 * Returns the type of the notification
	 * 
	 * @return the type of the notification
	 */
	Type getType();

	/**
	 * Returns the id of the announcement for which the notification is sent or
	 * null if the notification is not associated with an announcement
	 * 
	 * @return the id of the announcement for which the notification is sent or
	 *         null if the notification is not associated with an announcement
	 */
	Long getAnnouncementId();

	/**
	 * The method allows dynamically adding functionality to a notification
	 * instance
	 * 
	 * @param inVisitor
	 *            the visitor that adds behaviour to the notification. It may
	 *            not be null
	 */
	void accept(final INotificationVisitor inVisitor);
}
