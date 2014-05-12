package org.shared.traveller.client.domain.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The class represents a REST announcement instance used for communication
 * between the client and the server parts
 * 
 * @author "Ivan Ivanov"
 * 
 */
@JsonTypeName("anno")
public class Announcement implements IAnnouncement
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 2386542644705690994L;

	private static final String NULL_VISITOR = "Visitor cannot be null!";

	private final Long id;

	private final String from;

	private final String to;

	private final Date departureDate;

	private final Date departureTime;

	private final BigDecimal price;

	private final short seats;

	private final String depAddress;

	private final String vehicleName;

	private final String driverUsername;

	private final Status status;

	private final List<String> intermediatePts;

	/**
	 * The class is used to create new announcement instances
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static class AnnouncementBuilder implements IAnnouncement.IBuilder
	{
		private Long idField;

		private final String fromField;

		private final String toField;

		private final Date depDateField;

		private Date depTimeField;

		private BigDecimal priceField;

		private final short seatsField;

		private String depAddressField;

		private String vehicleNameField;

		private final String driverUsernameField;

		private Status statusField;

		private List<String> intermediatePtsField;

		/**
		 * Creates a new builder instance
		 * 
		 * @param inFrom
		 *            the from point for the announcement being built. It may
		 *            not be null
		 * @param inTo
		 *            the to point for the announcement being built. It may not
		 *            be null
		 * @param inDepDate
		 *            the departure date for the announcement being built. It
		 *            may not be null
		 * @param inSeats
		 *            the number of seats for the announcement being built
		 * @param inDriverUsername
		 *            the user name of the driver. It may not be null
		 */
		public AnnouncementBuilder(final String inFrom, final String inTo,
				final Date inDepDate, final short inSeats,
				final String inDriverUsername)
		{
			InstanceAsserter.assertNotNull(inFrom, "start point");
			InstanceAsserter.assertNotNull(inTo, "end point");
			InstanceAsserter.assertNotNull(inDepDate, "departure date");
			InstanceAsserter.assertNotNull(inDriverUsername,
					"driver's user name");

			fromField = inFrom;
			toField = inTo;
			depDateField = DeepCopier.copy(inDepDate);
			seatsField = inSeats;
			driverUsernameField = inDriverUsername;
		}

		@Override
		public IBuilder id(Long inId)
		{
			idField = inId;
			return this;
		}

		@Override
		public AnnouncementBuilder depTime(final Date inDepTime)
		{
			depTimeField = DeepCopier.copy(inDepTime);
			return this;
		}

		@Override
		public AnnouncementBuilder price(final BigDecimal inPrice)
		{
			priceField = DeepCopier.copy(inPrice);
			return this;
		}

		@Override
		public AnnouncementBuilder depAddress(final String inDepAddress)
		{
			depAddressField = inDepAddress;
			return this;
		}

		@Override
		public AnnouncementBuilder vehicleName(final String inVehName)
		{
			vehicleNameField = inVehName;
			return this;
		}

		@Override
		public AnnouncementBuilder status(final Status inStatus)
		{
			statusField = inStatus;
			return this;
		}

		@Override
		public AnnouncementBuilder intermediatePoints(
				final List<String> inIntermediatePts)
		{
			intermediatePtsField = DeepCopier.copy(inIntermediatePts);
			return this;
		}

		@Override
		public Announcement build()
		{
			return new Announcement(this);
		}
	}

	/**
	 * The constructor creates a new announcement using the information in the
	 * provided builder instance
	 * 
	 * @param inBuilder
	 *            the builder used to create the announcement
	 */
	private Announcement(final AnnouncementBuilder inBuilder)
	{
		id = inBuilder.idField;
		from = inBuilder.fromField;
		to = inBuilder.toField;
		departureDate = DeepCopier.copy(inBuilder.depDateField);
		departureTime = DeepCopier.copy(inBuilder.depTimeField);
		price = DeepCopier.copy(inBuilder.priceField);
		seats = inBuilder.seatsField;
		depAddress = inBuilder.depAddressField;
		vehicleName = inBuilder.vehicleNameField;
		driverUsername = inBuilder.driverUsernameField;
		intermediatePts = DeepCopier.copy(inBuilder.intermediatePtsField);
		status = inBuilder.statusField;
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
	public Status getStatus()
	{
		return status;
	}
}
