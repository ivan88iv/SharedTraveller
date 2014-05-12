package org.shared.traveller.client.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The interface represents the functionality an announcement sent by the
 * services should possess
 * 
 * @author "Ivan Ivanov"
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes(@Type(value = Announcement.class, name = "anno"))
public interface IAnnouncement extends Serializable
{
	/**
	 * Represents the status of an announcement
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public enum Status {
		/**
		 * When the announcement is still active
		 */
		ACTIVE,

		/**
		 * When the travel was completed
		 */
		COMPLETED,

		/**
		 * When the travel was cancelled
		 */
		CANCELLED;
	}

	/**
	 * The interface represents the common functionality for announcement
	 * builder classes
	 * 
	 * @author "Ivan Ivanov"
	 */
	public static interface IBuilder
	{
		/**
		 * Sets the new id of the announcement to be built
		 * 
		 * @param inId
		 *            the new id value
		 * @return the builder instance
		 */
		IBuilder id(final Long inId);

		/**
		 * The method sets the new value for the departure time of the built
		 * announcement
		 * 
		 * @param inDepTime
		 *            the new departure time value
		 * @return the builder used
		 */
		IBuilder depTime(final Date inDepTime);

		/**
		 * The method sets the new value for the price of the built announcement
		 * 
		 * @param inPrice
		 *            the new price value
		 * @return the builder used
		 */
		IBuilder price(final BigDecimal inPrice);

		/**
		 * The method sets the new value for the departure address of the built
		 * announcement
		 * 
		 * @param inPrice
		 *            the new departure address value
		 * @return the builder used
		 */
		IBuilder depAddress(final String inDepAddress);

		/**
		 * The method sets the new value for the name of the vehicle of the
		 * built announcement
		 * 
		 * @param inPrice
		 *            the new vehicle name
		 * @return the builder used
		 */
		IBuilder vehicleName(final String inVehName);

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
		 * The method sets the new intermediate points for the built
		 * announcement
		 * 
		 * @param inIntermediatePts
		 *            the new intermediate points
		 * @return the builder used
		 */
		IBuilder intermediatePoints(
				final List<String> inIntermediatePts);

		/**
		 * The method builds the resulting announcement
		 * 
		 * @return the resulting announcement
		 */
		Announcement build();
	}

	/**
	 * Returns the id of the current announcement
	 * 
	 * @return the id of the current announcement
	 */
	Long getId();

	/**
	 * Returns the from settlement of the announcement
	 * 
	 * @return the from settlement of the announcement
	 */
	String getFrom();

	/**
	 * Returns the to settlement of the announcement
	 * 
	 * @return the to settlement of the announcement
	 */
	String getTo();

	/**
	 * Returns the departure date of the announcement
	 * 
	 * @return the departure date of the announcement
	 */
	Date getDepartureDate();

	/**
	 * Returns the departure time of the announcement
	 * 
	 * @return the departure time of the announcement
	 */
	Date getDepartureTime();

	/**
	 * Returns the price for each traveller
	 * 
	 * @return the price for each traveller
	 */
	BigDecimal getPrice();

	/**
	 * Returns the number of free seats left for this travel
	 * 
	 * @return the number of free seats left for this travel
	 */
	short getSeats();

	/**
	 * Returns the exact address the travel will start from
	 * 
	 * @return the exact address the travel will start from
	 */
	String getDepAddress();

	/**
	 * Returns the name of the vehicle for the travel
	 * 
	 * @return the name of the vehicle for the travel
	 */
	String getVehicleName();

	/**
	 * Returns the user name of the driver
	 * 
	 * @return the user name of the driver
	 */
	String getDriverUsername();

	/**
	 * Returns the intermediate settlement points for this travel
	 * 
	 * @return the intermediate settlement points for this travel
	 */
	List<String> getIntermediatePts();

	/**
	 * Returns the status of the announcement
	 * 
	 * @return the status of the announcement
	 */
	Status getStatus();

	/**
	 * The method is used to allow adding of new functionality through the
	 * visitor pattern
	 * 
	 * @param inVisitor
	 *            the visitor to add new functionality
	 */
	void accept(final IAnnouncementVisitor inVisitor);
}
