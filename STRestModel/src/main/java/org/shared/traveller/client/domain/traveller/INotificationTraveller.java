package org.shared.traveller.client.domain.traveller;

import java.io.Serializable;

/**
 * The interface represents the user information necessary for sending
 * notifications
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface INotificationTraveller extends Serializable
{
	/**
	 * The interface is used for building a new notification traveller
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static interface IBuilder
	{
		/**
		 * Sets the id of the traveller
		 * 
		 * @param inId
		 *            the new id of the traveller
		 * @return the builder instance
		 */
		IBuilder id(final Long inId);

		/**
		 * Sets the user name of the traveller
		 * 
		 * @param inUsername
		 *            the new user name of the traveller
		 * @return the builder instance
		 */
		IBuilder username(final String inUsername);

		/**
		 * Sets the phone number of the traveller
		 * 
		 * @param inPhoneNumber
		 *            the new phone number of the traveller
		 * @return the builder instance
		 */
		IBuilder phoneNumber(final String inPhoneNumber);

		/**
		 * Sets the email address of the traveller
		 * 
		 * @param inEmail
		 *            the new email address of the traveller
		 * @return the builder instance
		 */
		IBuilder email(final String inEmail);

		/**
		 * Sets true if the traveller allows sms notifications
		 * 
		 * @param inAllow
		 *            if the traveller allows sms notifications
		 * @return the builder instance
		 */
		IBuilder allowSmsNotifications(final boolean inAllow);

		/**
		 * Sets true if the traveller allows email notifications
		 * 
		 * @param inAllow
		 *            if the traveller allows email notifications
		 * @return the builder instance
		 */
		IBuilder allowEmailNotifications(final boolean inAllow);

		/**
		 * The method creates a new notification traveller
		 * 
		 * @return the newly created notification traveller
		 */
		INotificationTraveller build();
	}

	/**
	 * Returns the id of the user
	 * 
	 * @return the id of the user
	 */
	Long getId();

	/**
	 * Returns the user name of the traveller
	 * 
	 * @return the user name of the traveller
	 */
	String getUsername();

	/**
	 * Returns the phone number of the traveller
	 * 
	 * @return the phone number of the traveller
	 */
	String getPhoneNumber();

	/**
	 * Returns the email address of the traveller
	 * 
	 * @return the email address of the traveller
	 */
	String getEmail();

	/**
	 * Returns true if the traveller can receive sms notifications
	 * 
	 * @return true if the travelller can receive sms notifications
	 */
	boolean isSmsNotificationAllowed();

	/**
	 * Returns true if the traveller can receive email notifications
	 * 
	 * @return true if the traveller can receive email notifications
	 */
	boolean isEmailNotificationAllowed();
}
