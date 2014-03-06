package org.shared.traveller.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.business.domain.jpa.AnnouncementEntity.Status;

public interface IPersistentAnnouncement
{
	IPersistentCity getStartPoint();

	IPersistentCity getEndPoint();

	String getAddress();

	Date getDepartureDate();

	Date getDepartureTime();

	IPersistentVehicle getVehicle();

	BigDecimal getPrice();

	Short getFreeSeats();

	Status getStatus();

	IPersistentTraveller getDriver();

	List<? extends IPersistentCity> getIntermediatePoints();
}
