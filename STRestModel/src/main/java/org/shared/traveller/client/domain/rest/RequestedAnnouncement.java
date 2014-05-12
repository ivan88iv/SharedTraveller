package org.shared.traveller.client.domain.rest;

import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.rest.PlainRequest;
import org.shared.traveller.utility.DeepCopier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The class represents an announcement which holds information about related to
 * its requests
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestedAnnouncement implements IRequestedAnnouncement
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -3903618258952658649L;

	/**
	 * The class is responsible for building new requested announcement
	 * instances
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static class Builder implements IBuilder
	{
		private Long idField;

		private String fromField;

		private String toField;

		private String driverField;

		private Date departureDateField;

		private short seatsField;

		private Status statusField;

		private List<? extends IPlainRequest> requestsField;

		@Override
		public IBuilder id(Long inId)
		{
			idField = inId;
			return this;
		}

		@Override
		public IBuilder from(String inFrom)
		{
			fromField = inFrom;
			return this;
		}

		@Override
		public IBuilder to(String inTo)
		{
			toField = inTo;
			return this;
		}

		@Override
		public IBuilder driver(String inDriver)
		{
			driverField = inDriver;
			return this;
		}

		@Override
		public IBuilder departureDate(Date inDate)
		{
			departureDateField = DeepCopier.copy(inDate);
			return this;
		}

		@Override
		public IBuilder seats(short inSeats)
		{
			seatsField = inSeats;
			return this;
		}

		@Override
		public IBuilder status(Status inStatus)
		{
			statusField = inStatus;
			return this;
		}

		@Override
		public IBuilder requests(List<? extends IPlainRequest> inRequests)
		{
			requestsField = DeepCopier.copy(inRequests);
			return this;
		}

		@Override
		public RequestedAnnouncement build()
		{
			return new RequestedAnnouncement(this);
		}
	}

	private final Long id;

	private final String from;

	private final String to;

	private final String driver;

	private final Date departureDate;

	private final short seats;

	private final Status status;

	@JsonDeserialize(contentAs = PlainRequest.class)
	private final List<? extends IPlainRequest> requests;

	/**
	 * Creates a new requested announcement using information from the provided
	 * builder
	 * 
	 * @param inBuilder
	 *            the builder used for creating the requested announcement. It
	 *            may not be null
	 */
	private RequestedAnnouncement(final Builder inBuilder)
	{
		this(inBuilder.idField, inBuilder.fromField, inBuilder.toField,
				inBuilder.driverField,
				inBuilder.departureDateField,
				inBuilder.seatsField, inBuilder.statusField,
				inBuilder.requestsField);
	}

	/**
	 * Creates a new requested announcement using the information provided
	 * 
	 * @param inId
	 *            the id of the new requested announcement
	 * @param inFrom
	 *            the name of the from point for this announcement
	 * @param inTo
	 *            the name of the to point for this announcement
	 * @param inDriver
	 *            the user name of the driver for the announcement
	 * @param inDate
	 *            the departure date of new requested announcement
	 * @param inSeats
	 *            the seats for the new requested announcement
	 * @param inStatus
	 *            the status of the new requested announcement
	 * @param inRequests
	 *            the requests made for the new requested announcement
	 */
	@JsonCreator
	private RequestedAnnouncement(
			@JsonProperty(value = "id") final Long inId,
			@JsonProperty(value = "from") final String inFrom,
			@JsonProperty(value = "to") final String inTo,
			@JsonProperty(value = "driver") final String inDriver,
			@JsonProperty(value = "departureDate") final Date inDate,
			@JsonProperty(value = "seats") final short inSeats,
			@JsonProperty(value = "status") final Status inStatus,
			@JsonProperty(value = "requests") final List<? extends IPlainRequest> inRequests)
	{
		id = inId;
		from = inFrom;
		to = inTo;
		driver = inDriver;
		departureDate = DeepCopier.copy(inDate);
		seats = inSeats;
		status = inStatus;
		requests = DeepCopier.copy(inRequests);
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public String getFrom()
	{
		return from;
	}

	@Override
	public String getTo()
	{
		return to;
	}

	@Override
	public String getDriver()
	{
		return driver;
	}

	@Override
	public Date getDepartureDate()
	{
		return DeepCopier.copy(departureDate);
	}

	@Override
	public short getSeats()
	{
		return seats;
	}

	@Override
	public Status getStatus()
	{
		return status;
	}

	@Override
	public List<? extends IPlainRequest> getRequests()
	{
		return DeepCopier.copy(requests);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("------------------Requested Announcement ------------\n");
		builder.append("id: ").append(id).append("\n");
		builder.append("from: ").append(from).append("\n");
		builder.append("to: ").append(to).append("\n");
		builder.append("driver: ").append(driver).append("\n");
		builder.append("departure date: ").append(departureDate).append("\n");
		builder.append("seats: ").append(seats).append("\n");
		builder.append("status: ").append(status).append("\n");
		builder.append("requests: ").append(requests).append("\n");
		builder.append("------------------------------------------------------\n");

		return builder.toString();
	}
}
