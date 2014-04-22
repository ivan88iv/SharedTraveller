package org.shared.traveller.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement.Status;

/**
 * The interface represents the functionality a persistent announcement
 * should possess
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentAnnouncement
{
	/**
	 * Returns the settlement the travel will start from
	 *
	 * @return the settlement the travel will start from
	 */
	IPersistentCity getStartPoint();

	/**
	 * Returns the settlement the travel ends
	 *
	 * @return the settlement the travel ends
	 */
	IPersistentCity getEndPoint();

	/**
	 * Returns the exact address the travel will start from
	 * @return
	 */
	String getAddress();

	/**
	 * Returns the date the travel will begin
	 *
	 * @return the date the travel will begin
	 */
	Date getDepartureDate();

	/**
	 * Returns the time the travel will begin
	 *
	 * @return the time the trabel will begin
	 */
	Date getDepartureTime();

	/**
	 * Returns the vehicle for the travel
	 *
	 * @return the vehicle for the travel
	 */
	IPersistentVehicle getVehicle();

	/**
	 * Returns the cost of the travel for each traveller
	 *
	 * @return the cost of the travel for each traveller
	 */
	BigDecimal getPrice();

	/**
	 * Returns the number of free seats for this travel
	 *
	 * @return the number of free seats for this travel
	 */
	Short getFreeSeats();

	/**
	 * The method changes the value of the free seats in the announcement
	 *
	 * @param inSeats the new value of the free seats
	 */
	void setFreeSeats(final Short inSeats);

	/**
	 * Returns the status of the announcement
	 *
	 * @return the status of the announcement
	 */
	Status getStatus();

	/**
	 * Returns the driver for this travel
	 *
	 * @return the driver for this travel
	 */
	IPersistentTraveller getDriver();

	/**
	 * Returns the intermediate settlements the travel will
	 * go through( if any)
	 *
	 * @return the intermediate settlements the travel will
	 * go through( if any)
	 */
	List<? extends IPersistentCity> getIntermediatePoints();
}
