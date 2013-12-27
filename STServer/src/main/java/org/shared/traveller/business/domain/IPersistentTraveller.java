package org.shared.traveller.business.domain;

import java.util.List;

public interface IPersistentTraveller
{
	long getId();

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
