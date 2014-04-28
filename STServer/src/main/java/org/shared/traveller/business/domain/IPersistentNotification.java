package org.shared.traveller.business.domain;

import java.util.Date;

import org.shared.traveller.business.domain.visitor.INotificationVisitor;
import org.shared.traveller.client.domain.INotification.Type;

public interface IPersistentNotification extends IPersistent
{
	public static interface IPersistentNotificationBuilder
	{
		IPersistentNotificationBuilder id(final Long inId);

		IPersistentNotificationBuilder description(final String inDescription);

		IPersistentNotificationBuilder seen(final Boolean inSeen);

		IPersistentNotification build();
	}

	Date getCreationDate();

	String getDescription();

	Boolean getSeen();

	Type getType();

	IPersistentTraveller getReceiver();

	IPersistentTraveller getSender();

	IPersistentAnnouncement getAnnouncement();

	void accept(final INotificationVisitor inVisitor);
}
