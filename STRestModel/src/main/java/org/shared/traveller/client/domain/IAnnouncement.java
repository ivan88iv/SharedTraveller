package org.shared.traveller.client.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;

/**
 * The interface represents the functionality an
 * announcement sent by the services should possess
 *
 * @author "Ivan Ivanov"
 *
 */
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
	 * The method is used to allow adding of new functionality
	 * through the visitor pattern
	 *
	 * @param inVisitor the visitor to add new functionality
	 */
	void accept(final IAnnouncementVisitor inVisitor);
}
