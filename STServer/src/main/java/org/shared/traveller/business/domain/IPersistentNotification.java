package org.shared.traveller.business.domain;

import java.util.Date;

public interface IPersistentNotification
{
	Date getCreationDate();

	String getText();

	Boolean getSeen();

	IPersistentTraveller getTraveller();
}
