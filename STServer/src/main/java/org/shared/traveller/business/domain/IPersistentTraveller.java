package org.shared.traveller.business.domain;

import java.util.List;

import org.shared.traveller.business.domain.jpa.NotificationEntity;

public interface IPersistentTraveller
{
	String getUsername();

	String getPassword();

	String getFbAuthToken();

	String getPhoneNUmber();

	String getEmail();

	String getFirstName();

	String getLastName();

	Integer getRatingCount();

	Integer getRating_sum();

	String getTravelCount();

	List<? extends IPersistentVehicle> getVehicles();

	List<NotificationEntity> getNotifications();
}
