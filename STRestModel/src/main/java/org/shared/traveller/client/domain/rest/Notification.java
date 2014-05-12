package org.shared.traveller.client.domain.rest;

import java.util.Date;

import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.visitor.INotificationVisitor;
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class represents a REST client domain instance
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class Notification implements INotification
{
	private final Date creationDate;

	private final String title;

	private final String description;

	private final Type type;

	private final Long announcementId;

	/**
	 * The class is used in order to create a new notification instance
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static class Builder implements INotification.IBuilder
	{
		private Date createionDateField;

		private String titleField;

		private String descriptionField;

		private Type typeField;

		private Long announcementIdField;

		@Override
		public IBuilder creationDate(Date inDate)
		{
			createionDateField = DeepCopier.copy(inDate);
			return this;
		}

		@Override
		public IBuilder title(String inTitle)
		{
			titleField = inTitle;
			return this;
		}

		@Override
		public IBuilder description(String inDescription)
		{
			descriptionField = inDescription;
			return this;
		}

		@Override
		public IBuilder type(Type inType)
		{
			typeField = inType;
			return this;
		}

		@Override
		public IBuilder announcementId(Long inId)
		{
			announcementIdField = inId;
			return this;
		}

		@Override
		public INotification build()
		{
			return new Notification(this);
		}
	}

	/**
	 * Creates a new notification instance using the provided builder instance
	 * 
	 * @param inBuilder
	 *            the builder used in the creation. It may not be null.
	 */
	private Notification(final Builder inBuilder)
	{
		InstanceAsserter.assertNotNull(inBuilder, "builder");

		creationDate = DeepCopier.copy(inBuilder.createionDateField);
		title = inBuilder.titleField;
		description = inBuilder.descriptionField;
		type = inBuilder.typeField;
		announcementId = inBuilder.announcementIdField;
	}

	/**
	 * Creates a new REST notification
	 * 
	 * @param inType
	 *            the type of the notification. It may not be null.
	 * @param inCreationDate
	 *            the creation date of the notification. It may not be null.
	 * @param inTitle
	 *            the title of the notification
	 * @param inDescription
	 *            the description of the notification
	 * @param inAnnId
	 *            the id of the announcement to which the notification is
	 *            associated( if any)
	 */
	@JsonCreator
	private Notification(@JsonProperty("type") final Type inType,
			@JsonProperty("creationDate") final Date inCreationDate,
			@JsonProperty("title") final String inTitle,
			@JsonProperty("description") final String inDescription,
			@JsonProperty("announcementId") final Long inAnnId)
	{
		InstanceAsserter.assertNotNull(inType, "type");
		InstanceAsserter.assertNotNull(inCreationDate, "creation date");

		type = inType;
		creationDate = DeepCopier.copy(inCreationDate);
		title = inTitle;
		description = inDescription;
		announcementId = inAnnId;
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
	public Long getAnnouncementId()
	{
		return announcementId;
	}

	@Override
	public void accept(INotificationVisitor inVisitor)
	{
		InstanceAsserter.assertNotNull(inVisitor, "visitor");

		inVisitor.visit(this);
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("--------------------- Notification -------------------\n");
		builder.append("creation date: ").append(creationDate).append("\n");
		builder.append("title: ").append(title).append("\n");
		builder.append("description: ").append(description).append("\n");
		builder.append("type: ").append(type).append("\n");
		builder.append("announcement's id: ").append(announcementId)
				.append("\n");
		builder.append("------------------------------------------------------\n");

		return builder.toString();
	}
}
