package org.shared.traveller.client.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IPlainRequest;

/**
 * The interface represents the request part of the announcement information
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestedAnnouncement extends Serializable
{
	/**
	 * The interface represents the common functionality for requested
	 * announcement builder classes
	 * 
	 * @author "Ivan Ivanov"
	 */
	public static interface IBuilder
	{
		/**
		 * Sets the new id of the requested announcement to be built
		 * 
		 * @param inId
		 *            the new id value
		 * @return the builder instance
		 */
		IBuilder id(final Long inId);

		/**
		 * Sets the new from point for the announcement to be built
		 * 
		 * @param inFrom
		 *            the new from point
		 * @return the builder instance
		 */
		IBuilder from(final String inFrom);

		/**
		 * Sets the new to point for the announcement to be built
		 * 
		 * @param inTo
		 *            the new to point
		 * @return the builder instance
		 */
		IBuilder to(final String inTo);

		/**
		 * Sets the new to driver's user name for the announcement to be built
		 * 
		 * @param inDriver
		 *            the new driver's user name
		 * @return the builder instance
		 */
		IBuilder driver(final String inDriver);

		/**
		 * Sets a new departure date for the requested announcement
		 * 
		 * @param inDate
		 *            the new date value
		 * @return the builder instance
		 */
		IBuilder departureDate(final Date inDate);

		/**
		 * Sets the number of free seats for the requested announcement
		 * 
		 * @param inSeats
		 *            the free seats for the new requested announcement
		 * @return the builder instance
		 */
		IBuilder seats(final short inSeats);

		/**
		 * The method sets the new value for the status of the built
		 * announcement
		 * 
		 * @param inStatus
		 *            the new status value
		 * @return the builder used
		 */
		IBuilder status(final Status inStatus);

		/**
		 * The method sets the new request instances associated with the current
		 * announcement
		 * 
		 * @param inRequests
		 *            the requests to be set
		 * @return the builder instance
		 */
		IBuilder requests(
				final List<? extends IPlainRequest> inRequests);

		/**
		 * The method builds the resulting announcement
		 * 
		 * @return the resulting announcement
		 */
		IRequestedAnnouncement build();
	}

	/**
	 * Returns the id of the current announcement
	 * 
	 * @return the id of the current announcement
	 */
	Long getId();

	/**
	 * Returns the name of the start point for this announcement's travel
	 * 
	 * @return the name of the start point for this announcement's travel
	 */
	String getFrom();

	/**
	 * Returns the name of the end point for this announcement's travel
	 * 
	 * @return the name of the end point for this announcement's travel
	 */
	String getTo();

	/**
	 * Returns the departure date of the announcement
	 * 
	 * @return the departure date of the announcement
	 */
	Date getDepartureDate();

	/**
	 * Returns the user name of the driver for the requested announcement
	 * 
	 * @return the user name of the driver for the requested announcement
	 */
	String getDriver();

	/**
	 * Returns the number of free seats left for this travel
	 * 
	 * @return the number of free seats left for this travel
	 */
	short getSeats();

	/**
	 * Returns the status of the announcement
	 * 
	 * @return the status of the announcement
	 */
	Status getStatus();

	/**
	 * Returns the request instances that are associated with the current
	 * announcement
	 * 
	 * @return the request instances that are associated with the current
	 *         announcement
	 */
	List<? extends IPlainRequest> getRequests();
}
