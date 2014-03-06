package org.shared.traveller.business.domain;

import org.shared.traveller.business.domain.enumeration.RequestStatus;

public interface IPersistentRequest
{
	Long getId();

	IPersistentTraveller getTraveller();

	IPersistentAnnouncement getAnnouncement();

	RequestStatus getStatus();
}
