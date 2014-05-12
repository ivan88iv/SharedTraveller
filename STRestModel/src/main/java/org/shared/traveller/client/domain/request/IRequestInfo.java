package org.shared.traveller.client.domain.request;

import java.io.Serializable;
import java.util.Date;

import org.shared.traveller.client.domain.request.rest.RequestInfo;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;

/**
 * The event is used to represent the information for an announcement's request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestInfo extends Serializable
{
	/**
	 * The interface represents the common functionality each request
	 * information builder should possess
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public interface IBuilder
	{
		/**
		 * Sets a new value for the request's id
		 * 
		 * @param inId
		 *            the new value to be set
		 * @return the current request info builder
		 */
		IBuilder id(final Long inId);

		/**
		 * Sets a new user name to the sender field
		 * 
		 * @param inSender
		 *            the new sender user name
		 * @return the builder
		 */
		IBuilder sender(final String inSender);

		/**
		 * Sets a new settlement for a start point of the travel
		 * 
		 * @param inFrom
		 *            the new start point
		 * @return the builder
		 */
		IBuilder fromPoint(final String inFrom);

		/**
		 * Sets a new settlement for a end point of the travel
		 * 
		 * @param inTo
		 *            the new end point
		 * @return the builder
		 */
		IBuilder toPoint(final String inTo);

		/**
		 * Sets a new date for the departure
		 * 
		 * @param inDepDate
		 *            the new departure date
		 * @return the builder
		 */
		IBuilder departureDate(final Date inDepDate);

		/**
		 * Sets a new value for the request's driver
		 * 
		 * @param inDriver
		 *            the new request's driver
		 * @return the builder
		 */
		IBuilder driver(final INotificationTraveller inDriver);

		/**
		 * Sets a new status value for the request
		 * 
		 * @param inStatus
		 *            the new request's status
		 * @return the builder
		 */
		IBuilder status(final RequestStatus inStatus);

		/**
		 * The method builds a new request information instance
		 * 
		 * @return the newly constructed instance
		 */
		RequestInfo build();
	}

	/**
	 * The method returns the id of the request
	 * 
	 * @return the id of the request
	 */
	Long getId();

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
	 * Returns the date of the departure*
	 * 
	 * @return the date of the departure
	 */
	Date getDepartureDate();

	/**
	 * Returns the driver for the request
	 * 
	 * @return the driver for the request
	 */
	INotificationTraveller getDriver();

	/**
	 * Returns the status of the request
	 * 
	 * @return the status of the request
	 */
	RequestStatus getStatus();
}
