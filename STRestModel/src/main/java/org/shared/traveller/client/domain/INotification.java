package org.shared.traveller.client.domain;

import java.util.Date;

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
	 * Returns the date the notification was created on
	 * 
	 * @return the date the notification was created on
	 */
	Date getCreationDate();

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
	 * Returns the traveller this notification is connected to
	 * 
	 * @return the traveller this notification is connected to
	 */
	ITraveller getTraveller();
}
