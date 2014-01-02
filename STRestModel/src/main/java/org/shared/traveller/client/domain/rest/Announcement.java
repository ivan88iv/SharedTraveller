package org.shared.traveller.client.domain.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;
import org.shared.traveller.utility.DeepCopier;

public class Announcement implements IAnnouncement
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 2386542644705690994L;

	private static final String NULL_VISITOR = "Visitor cannot be null!";

	private String from;

	private String to;

	private Date departureDate;

	private Date departureTime;

	private BigDecimal price;

	private short seats;

	private String depAddress;

	private String vehicleName;

	private String driverUsername;

	private String status;

	private List<String> intermediatePts;

	public static class AnnouncementBuilder
	{
		private static final String NULL_FROM_POINT = "Start point cannot be null.";

		private static final String NULL_TO_POINT = "End point cannot be null.";

		private static final String NULL_DEP_DATE = "Departure date cannot be null.";

		private static final String NULL_USERNAME = "The driver's username cannot be null.";

		private String fromField;

		private String toField;

		private Date depDateField;

		private Date depTimeField;

		private BigDecimal priceField;

		private short seatsField;

		private String depAddressField;

		private String vehicleNameField;

		private String driverUsernameField;

		private String statusField;

		private List<String> intermediatePtsField;

		public AnnouncementBuilder(final String inFrom, final String inTo, final Date inDepDate, final short inSeats, final String inDriverUsername)
		{
			assert null != inFrom : NULL_FROM_POINT;
			assert null != inTo : NULL_TO_POINT;
			assert null != inDepDate : NULL_DEP_DATE;
			assert null != inDriverUsername : NULL_USERNAME;

			fromField = inFrom;
			toField = inTo;
			depDateField = DeepCopier.copy(inDepDate);
			seatsField = inSeats;
			driverUsernameField = inDriverUsername;
		}

		public AnnouncementBuilder depTime(final Date inDepTime)
		{
			depTimeField = DeepCopier.copy(inDepTime);
			return this;
		}

		public AnnouncementBuilder price(final BigDecimal inPrice)
		{
			priceField = DeepCopier.copy(inPrice);
			return this;
		}

		public AnnouncementBuilder depAddress(final String inDepAddress)
		{
			depAddressField = inDepAddress;
			return this;
		}

		public AnnouncementBuilder vehicleName(final String inVehName)
		{
			vehicleNameField = inVehName;
			return this;
		}

		public AnnouncementBuilder status(final String inStatus)
		{
			statusField = inStatus;
			return this;
		}

		public AnnouncementBuilder intermediatePoints(final List<String> inIntermediatePts)
		{
			intermediatePtsField = new ArrayList<String>(inIntermediatePts);
			return this;
		}

		public Announcement build()
		{
			return new Announcement(this);
		}
	}

	public Announcement()
	{

	}

	private Announcement(final AnnouncementBuilder inBuilder)
	{
		from = inBuilder.fromField;
		to = inBuilder.toField;
		departureDate = DeepCopier.copy(inBuilder.depDateField);
		departureTime = DeepCopier.copy(inBuilder.depTimeField);
		price = DeepCopier.copy(inBuilder.priceField);
		seats = inBuilder.seatsField;
		depAddress = inBuilder.depAddressField;
		vehicleName = inBuilder.vehicleNameField;
		driverUsername = inBuilder.driverUsernameField;
		status = inBuilder.statusField;
		intermediatePts = DeepCopier.copy(inBuilder.intermediatePtsField);
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
	public Date getDepartureDate()
	{
		return DeepCopier.copy(departureDate);
	}

	@Override
	public Date getDepartureTime()
	{
		return DeepCopier.copy(departureTime);
	}

	@Override
	public BigDecimal getPrice()
	{
		return DeepCopier.copy(price);
	}

	@Override
	public short getSeats()
	{
		return seats;
	}

	@Override
	public String getDepAddress()
	{
		return depAddress;
	}

	@Override
	public List<String> getIntermediatePts()
	{
		return DeepCopier.copy(intermediatePts);
	}

	@Override
	public void accept(final IAnnouncementVisitor inVisitor)
	{
		assert null != inVisitor : NULL_VISITOR;
		inVisitor.visit(this);
	}

	@Override
	public String getVehicleName()
	{
		return vehicleName;
	}

	@Override
	public String getDriverUsername()
	{
		return driverUsername;
	}

	@Override
	public String getStatus()
	{
		return status;
	}
}
