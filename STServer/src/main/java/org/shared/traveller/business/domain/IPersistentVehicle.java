package org.shared.traveller.business.domain;

import java.util.Date;

/**
 * The interface represents a persistent vehicle instance
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentVehicle extends IPersistent
{
	/**
	 * Returns the name that is displayed for the current vehicle
	 *
	 * @return the name that is displayed for the current vehicle
	 */
	String getDisplayName();

	/**
	 * Returns the make of the vehicle
	 *
	 * @return the make of the vehicle
	 */
	String getMake();

	/**
	 * Returns the model of the vehicle
	 *
	 * @return the model of the vehicle
	 */
	String getModel();

	/**
	 * Returns the year the vehicle was manufactured
	 *
	 * @return the year the vehicle was manufactured
	 */
	Date getYearOfProduction();

	/**
	 * Returns the registration number of the vehicle
	 *
	 * @return the registration number of the vehicle
	 */
	String getRegNumber();

	/**
	 * Returns the vehicle's colour
	 *
	 * @return the vehicle's colour
	 */
	String getColour();

	/**
	 * Returns the number of seats in the vehicle
	 *
	 * @return the number of seats in the vehicle
	 */
	Short getSeats();

	/**
	 * Returns the description for the vehicle
	 *
	 * @return the description for the vehicle
	 */
	String getDescription();

	/**
	 * Returns true if the vehicle has a climate control unit/air-conditioning
	 * system
	 *
	 * @return true if the vehicle has a climate control unit/air-conditioning
	 *         system
	 */
	Boolean getCcu();

	/**
	 * Returns true if the vehicle has an air bag
	 *
	 * @return true if the vehicle has an air bag
	 */
	Boolean getAirbag();

	/**
	 * Returns the owner of the vehicle
	 *
	 * @return the owner of the vehicle
	 */
	IPersistentTraveller getOwner();
}
