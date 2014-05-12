package org.shared.traveller.client.domain.request.rest;

import java.util.Date;

import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;
import org.shared.traveller.client.domain.traveller.rest.NotificationTraveller;
import org.shared.traveller.utility.DeepCopier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The class represents a request related information
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestInfo implements IRequestInfo
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -3391106766190335044L;

	private final Long id;

	private final String sender;

	private final String fromPoint;

	private final String toPoint;

	private final Date departureDate;

	@JsonDeserialize(as = NotificationTraveller.class)
	private final INotificationTraveller driver;

	private final RequestStatus status;

	/**
	 * The class is responsible for building a new request info instance
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static class RequestInfoBuilder implements IRequestInfo.IBuilder
	{
		private Long idField;

		private String senderField;

		private String fromField;

		private String toField;

		private Date departureDateField;

		private INotificationTraveller driverField;

		private RequestStatus statusField;

		@Override
		public RequestInfoBuilder id(final Long inId)
		{
			idField = inId;
			return this;
		}

		@Override
		public RequestInfoBuilder sender(final String inSender)
		{
			senderField = inSender;
			return this;
		}

		@Override
		public RequestInfoBuilder fromPoint(final String inFrom)
		{
			fromField = inFrom;
			return this;
		}

		@Override
		public RequestInfoBuilder toPoint(final String inTo)
		{
			toField = inTo;
			return this;
		}

		@Override
		public RequestInfoBuilder departureDate(final Date inDepDate)
		{
			departureDateField = DeepCopier.copy(inDepDate);
			return this;
		}

		@Override
		public RequestInfoBuilder driver(final INotificationTraveller inDriver)
		{
			driverField = inDriver;
			return this;
		}

		@Override
		public RequestInfoBuilder status(final RequestStatus inStatus)
		{
			statusField = inStatus;
			return this;
		}

		@Override
		public RequestInfo build()
		{
			return new RequestInfo(this);
		}
	}

	/**
	 * The constructor creates a request information instance by using the
	 * special builder class provided
	 * 
	 * @param inBuilder
	 */
	private RequestInfo(final RequestInfoBuilder inBuilder)
	{
		this(inBuilder.idField, inBuilder.senderField, inBuilder.fromField,
				inBuilder.toField, inBuilder.departureDateField,
				inBuilder.driverField, inBuilder.statusField);
	}

	/**
	 * The constructor creates a new request info instance
	 * 
	 * @param inId
	 *            the id of the request
	 * @param inSender
	 *            the user name of the request's sender
	 * @param inFrom
	 *            the start point of the travel for which the request is posted
	 * @param inTo
	 *            the end point of the travel for which the request is posted
	 * @param inDepDate
	 *            the departure date of the travel
	 * @param inDriver
	 *            the driver for the request
	 * @param inStatus
	 *            the status of the request
	 */
	@JsonCreator
	private RequestInfo(
			@JsonProperty(value = "id") final Long inId,
			@JsonProperty(value = "sender") final String inSender,
			@JsonProperty(value = "fromPoint") final String inFrom,
			@JsonProperty(value = "toPoint") final String inTo,
			@JsonProperty(value = "departureDate") final Date inDepDate,
			@JsonProperty(value = "driver") final INotificationTraveller inDriver,
			@JsonProperty(value = "status") final RequestStatus inStatus)
	{
		id = inId;
		sender = inSender;
		fromPoint = inFrom;
		toPoint = inTo;
		departureDate = DeepCopier.copy(inDepDate);
		driver = inDriver;
		status = inStatus;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public String getSender()
	{
		return sender;
	}

	@Override
	public String getFromPoint()
	{
		return fromPoint;
	}

	@Override
	public String getToPoint()
	{
		return toPoint;
	}

	@Override
	public Date getDepartureDate()
	{
		return DeepCopier.copy(departureDate);
	}

	@Override
	public INotificationTraveller getDriver()
	{
		return driver;
	}

	@Override
	public RequestStatus getStatus()
	{
		return status;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();

		builder.append("------------- Requst Info -----------------\n");
		builder.append("id: ").append(id).append("\n");
		builder.append("sender name: " + sender + "\n");
		builder.append("from settlement: " + fromPoint + "\n");
		builder.append("to settlement: " + toPoint + "\n");
		builder.append("departure date: " + departureDate + "\n");
		builder.append("driver: " + driver + "\n");
		builder.append("status: " + status + "\n");
		builder.append("-------------------------------------------\n");
		return builder.toString();
	}
}
