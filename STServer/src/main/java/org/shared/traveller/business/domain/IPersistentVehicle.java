package org.shared.traveller.business.domain;

import java.util.Date;

public interface IPersistentVehicle
{
	long getId();

	String getDisplayName();

	String getMake();

	String getModel();

	Date getYearOfProduction();

	String getRegNumber();

	String getColor();

	Short getSeats();

	String getDesc();

	Boolean getCcu();

	Boolean getAirbag();

	IPersistentTraveller getOwner();
}
