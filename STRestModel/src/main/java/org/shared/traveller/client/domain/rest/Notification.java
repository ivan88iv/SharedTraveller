package org.shared.traveller.client.domain.rest;

import java.util.Date;

import org.shared.traveller.client.domain.INotification;
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

	private final String description;

	private final Type type;

	/**
	 * Creates a new REST notification
	 * 
	 * @param inType
	 *            the type of the notification. It may not be null.
	 * @param inCreationDate
	 *            the creation date of the notification. It may not be null.
	 * @param inDescription
	 *            the description of the notification
	 */
	@JsonCreator
	public Notification(@JsonProperty("type") final Type inType,
			@JsonProperty("creationDate") final Date inCreationDate,
			@JsonProperty("description") final String inDescription)
	{
		InstanceAsserter.assertNotNull(inType, "type");
		InstanceAsserter.assertNotNull(inCreationDate, "creation date");

		type = inType;
		creationDate = DeepCopier.copy(inCreationDate);
		description = inDescription;
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
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("--------------------- Notification -------------------\n");
		builder.append("creation date: ").append(creationDate).append("\n");
		builder.append("description: ").append(description).append("\n");
		builder.append("type: ").append(type).append("\n");
		builder.append("------------------------------------------------------\n");

		return builder.toString();
	}
}
