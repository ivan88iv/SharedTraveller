package org.ai.shared.traveller.notification.social;

/**
 * The interface represents notification being sent outside the application i.e.
 * via phone or email
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface ISocialNotification
{
	/**
	 * Returns the notification message sent
	 * 
	 * @return the notification message sent
	 */
	String getMessage();
}
