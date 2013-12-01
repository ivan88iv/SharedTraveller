package org.shared.traveller.rest.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.shared.traveller.utility.DeepCopier;

public class Announcement
{
	private String from;

	private String to;

	private Date depDate;

	private Date depTime;

	private BigDecimal price;

	private int seats;

	private String depAddress;

	private int vehicleId;

	private List<String> intermediatePts;

	public static class AnnouncementBuilder
	{
		private static final String NULL_FROM_POINT = "Start point cannot be null.";

		private static final String NULL_TO_POINT = "End point cannot be null.";

		private static final String NULL_DEP_DATE = "Departure date cannot be null.";

		private final String fromField;

		private final String toField;

		private final Date depDateField;

		private Date depTimeField;

		private BigDecimal priceField;

		private int seatsField;

		private String depAddressField;

		private int vehicleIdField;

		private List<String> intermediatePtsField;

		public AnnouncementBuilder(final String inFrom, final String inTo,
				final Date inDepDate)
		{
			assert null != inFrom : NULL_FROM_POINT;
			assert null != inTo : NULL_TO_POINT;
			assert null != inDepDate : NULL_DEP_DATE;

			fromField = inFrom;
			toField = inTo;
			depDateField = DeepCopier.copy(inDepDate);
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

		public AnnouncementBuilder seats(final int inSeats)
		{
			seatsField = inSeats;
			return this;
		}

		public AnnouncementBuilder depAddress(final String inDepAddress)
		{
			depAddressField = inDepAddress;
			return this;
		}

		public AnnouncementBuilder vehicleId(final int inVehId)
		{
			vehicleIdField = inVehId;
			return this;
		}

		public AnnouncementBuilder intermediatePoints(
				final List<String> inIntermediatePts)
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
		super();
	}

	private Announcement(final AnnouncementBuilder inBuilder)
	{
		from = inBuilder.fromField;
		to = inBuilder.toField;
		depDate = inBuilder.depDateField;
		depTime = inBuilder.depTimeField;
		price = inBuilder.priceField;
		seats = inBuilder.seatsField;
		depAddress = inBuilder.depAddressField;
		vehicleId = inBuilder.vehicleIdField;
		intermediatePts = inBuilder.intermediatePtsField;
	}

	public String getFrom()
	{
		return from;
	}

	public String getTo()
	{
		return to;
	}

	public Date getDepartureDate()
	{
		return DeepCopier.copy(depDate);
	}

	public Date getDepartureTime()
	{
		return DeepCopier.copy(depTime);
	}

	public BigDecimal getPrice()
	{
		return DeepCopier.copy(price);
	}

	public int getSeats()
	{
		return seats;
	}

	public String getDepAddress()
	{
		return depAddress;
	}

	public int getVehicleId()
	{
		return vehicleId;
	}

	public List<String> getIntermediatePts()
	{
		return new ArrayList<String>(intermediatePts);
	}
}
