package org.shared.traveller.client.domain.rest;

import java.util.Date;

import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.utility.DeepCopier;

public class Notification implements INotification
{
	private final Date creationDate;

	private final String description;

	private final Type type;

	private final Traveller traveller;

	public static class NotificationBuilder
	{
		private final Date creationDateField;

		private String descriptionField;

		private final Type typeField;

		private final Traveller travellerField;

		public NotificationBuilder(final Type inType,
				final Traveller inTraveller,
				final Date inCreationDate)
		{
			typeField = inType;
			travellerField = inTraveller;
			creationDateField = DeepCopier.copy(inCreationDate);
		}

		public NotificationBuilder descrition(final String inDescription)
		{
			descriptionField = inDescription;
			return this;
		}

		public Notification build()
		{
			return new Notification(this);
		}
	}

	private Notification(final NotificationBuilder inBuilder)
	{
		creationDate = DeepCopier.copy(inBuilder.creationDateField);
		description = inBuilder.descriptionField;
		type = inBuilder.typeField;
		traveller = inBuilder.travellerField;
	}

	@Override
	public Date getCreationDate()
	{
		return DeepCopier.copy(creationDate);
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public Traveller getTraveller()
	{
		return traveller;
	}
}
