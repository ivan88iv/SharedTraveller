package org.shared.traveller.client.domain.rest;

import java.util.Date;

import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.DeepCopier;

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

	private Long id;

	private String sender;

	private String fromPoint;

	private String toPoint;

	private Date departureDate;

	private String driver;

	private String driverPhone;

	private RequestStatus status;

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

		private String driverField;

		private String driverPhoneField;

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
		public RequestInfoBuilder driverUsername(final String inUsername)
		{
			driverField = inUsername;
			return this;
		}

		@Override
		public RequestInfoBuilder driverPhone(final String inDriverPhone)
		{
			driverPhoneField = inDriverPhone;
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
	 * This constructor is merely intended to be used for deserialization
	 * purposes. It should not be used by any user code.
	 */
	protected RequestInfo()
	{
		// used for JSON deserialization purposes
	}

	/**
	 * The constructor creates a request information instance by using the
	 * special builder class provided
	 *
	 * @param inBuilder
	 */
	private RequestInfo(final RequestInfoBuilder inBuilder)
	{
		id = inBuilder.idField;
		sender = inBuilder.senderField;
		fromPoint = inBuilder.fromField;
		toPoint = inBuilder.toField;
		departureDate = DeepCopier.copy(inBuilder.departureDateField);
		driver = inBuilder.driverField;
		driverPhone = inBuilder.driverPhoneField;
		status = inBuilder.statusField;
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
	public String getDriver()
	{
		return driver;
	}

	@Override
	public String getDriverPhone()
	{
		return driverPhone;
	}

	@Override
	public RequestStatus getStatus()
	{
		return status;
	}

	@Override
	public void setStatus(RequestStatus inStatus)
	{
		status = inStatus;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();

		builder.append("------------- Requst Info -----------------\n");
		builder.append("sender name: " + sender + "\n");
		builder.append("from settlement: " + fromPoint + "\n");
		builder.append("to settlement: " + toPoint + "\n");
		builder.append("departure date: " + departureDate + "\n");
		builder.append("driver name: " + driver + "\n");
		builder.append("status: " + status + "\n");
		builder.append("-------------------------------------------\n");
		return builder.toString();
	}
}
