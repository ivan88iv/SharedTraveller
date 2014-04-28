package org.shared.traveller.business.domain;

import java.util.List;

import org.shared.traveller.business.domain.jpa.NotificationEntity;

/**
 * The interface represents a persistent traveller instance
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentTraveller extends IPersistent
{
	/**
	 * Returns the user name of the traveller
	 *
	 * @return the user name of the traveller
	 */
	String getUsername();

	/**
	 * Returns the password for the traveller
	 *
	 * @return the password for the traveller
	 */
	String getPassword();

	/**
	 * Returns the facebook authentication token of the traveller
	 *
	 * @return the facebook authentication token of the traveller
	 */
	String getFbAuthToken();

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
	 * Returns the first name of the traveller
	 *
	 * @return the first name of the traveller
	 */
	String getFirstName();

	/**
	 * Returns the last name of the traveller
	 *
	 * @return the last name of the traveller
	 */
	String getLastName();

	/**
	 * Returns the rating count for this traveller
	 *
	 * @return the rating count for this traveller
	 */
	Integer getRatingCount();

	/**
	 * Returns the rating sum for this traveller
	 *
	 * @return the rating sum for this traveller
	 */
	Integer getRatingSum();

	/**
	 * Returns the count of travels made by this traveller
	 *
	 * @return the count of travels made by this traveller
	 */
	String getTravelCount();

	/**
	 * Returns the traveller's vehicles
	 *
	 * @return the traveller's vehicles
	 */
	List<? extends IPersistentVehicle> getVehicles();

	/**
	 * Returns the notifications received by the traveller
	 *
	 * @return the notifications received by the traveller
	 */
	List<NotificationEntity> getReceivedNotifications();

	/**
	 * Returns the notifications sent by the traveller
	 *
	 * @return the notifications sent by the traveller
	 */
	List<NotificationEntity> getSentNotifications();
}
