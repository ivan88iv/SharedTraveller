package org.shared.traveller.client.domain.request.rest;

import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;
import org.shared.traveller.client.domain.traveller.rest.NotificationTraveller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The class represents request instances which have no information about the
 * announcements they are associated with
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class PlainRequest implements IPlainRequest
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 8366807378557638235L;

	private final Long id;

	private final RequestStatus status;

	@JsonDeserialize(as = NotificationTraveller.class)
	private final INotificationTraveller sender;

	/**
	 * The constructor creates a new plain request instance
	 * 
	 * @param inId
	 *            the id of the instance
	 * @param inStatus
	 *            the status of the instance
	 * @param inSender
	 *            the user name of the user that has sent the request
	 * @param inSenderPhone
	 *            the phone of the sender
	 */
	@JsonCreator
	public PlainRequest(
			@JsonProperty(value = "id") final Long inId,
			@JsonProperty(value = "status") final RequestStatus inStatus,
			@JsonProperty(value = "sender") final INotificationTraveller inSender)
	{
		id = inId;
		status = inStatus;
		sender = inSender;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public RequestStatus getStatus()
	{
		return status;
	}

	@Override
	public INotificationTraveller getSender()
	{
		return sender;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("------------------Plain Request ------------\n");
		builder.append("id: ").append(id).append("\n");
		builder.append("status: ").append(status).append("\n");
		builder.append("sender: ").append(sender).append("\n");
		builder.append("--------------------------------------------\n");

		return builder.toString();
	}
}
