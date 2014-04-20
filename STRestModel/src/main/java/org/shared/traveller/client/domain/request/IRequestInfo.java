package org.shared.traveller.client.domain.request;

import java.io.Serializable;
import java.util.Date;

import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.rest.RequestInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The event is used to represent the information for an announcement's request
 * 
 * @author "Ivan Ivanov"
 * 
 */
@JsonDeserialize(as = RequestInfo.class)
public interface IRequestInfo extends Serializable
{
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

	/**
	 * Returns the status of the request
	 * 
	 * @return the status of the request
	 */
	RequestStatus getStatus();

	/**
	 * Returns the status of the announcement for which this request has been
	 * sent
	 * 
	 * @return the status of the announcement for which this request has been
	 *         sent
	 */
	Status getAnnouncementStatus();
}
