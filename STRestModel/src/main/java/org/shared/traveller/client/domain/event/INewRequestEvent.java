package org.shared.traveller.client.domain.event;

import java.io.Serializable;
import java.util.Date;

/**
 * The event is used to create a new request instance
 *
 * @author "Ivan Ivanov"
 *
 */
public interface INewRequestEvent extends Serializable
{
	/**
	 * The method returns the user name of the sender
	 *
	 * @return the user name of the sender
	 */
	String getSender();

	/**
	 * Returns the name of the start point of the journey
	 *
	 * @return the name of the start point of the journey
	 */
	String getFromPoint();

	/**
	 * Returns the name of the end point of the journey
	 *
	 * @return the name of the end point of the journey
	 */
	String getToPoint();

	/**
	 * Returns the date of the departure
	 *
	 * @return the date of the departure
	 */
	Date getDepartureDate();

	/**
	 * Returns the user name of the driver
	 *
	 * @return the user name of the driver
	 */
	String getDriver();
}
