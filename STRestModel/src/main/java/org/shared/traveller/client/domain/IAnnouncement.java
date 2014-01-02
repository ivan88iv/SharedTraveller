package org.shared.traveller.client.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;

public interface IAnnouncement extends Serializable
{
	String getFrom();

	String getTo();

	Date getDepartureDate();

	Date getDepartureTime();

	BigDecimal getPrice();

	short getSeats();

	String getDepAddress();

	String getVehicleName();

	String getDriverUsername();

	List<String> getIntermediatePts();

	void accept(final IAnnouncementVisitor inVisitor);

	String getStatus();
}
