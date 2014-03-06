package org.shared.traveller.business.domain.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.utility.DeepCopier;

@Entity(name = "Announcement")
@Table(name = "announcement")
@NamedQueries({
	@NamedQuery(name = "Announcement.loadAnnouncement",
			query = "SELECT ann FROM Announcement ann "
				+ "WHERE ann.startPoint.name = :startPt AND "
				+ "ann.endPoint.name = :endPt "
				+ "AND ann.departureDate = :depDate AND "
				+ "ann.driver.username = :driver")
})
public class AnnouncementEntity extends AbstractEntity implements
		IPersistentAnnouncement
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -289136650774029639L;

	public enum Status {
		ACTIVE,

		COMPLETED,

		CANCELLED;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", updatable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "START_POINT_ID")
	private CityEntity startPoint;

	@OneToOne
	@JoinColumn(name = "END_POINT_ID")
	private CityEntity endPoint;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DEPARTURE_DATE")
	@Temporal(TemporalType.DATE)
	private Date departureDate;

	@Column(name = "DEPARTURE_TIME")
	private Date departureTime;

	@OneToOne
	@JoinColumn(name = "VEHICLE_ID")
	private VehicleEntity vehicle;

	@Column(name = "PRICE")
	private BigDecimal price;

	@Column(name = "FREE_SEATS")
	private Short freeSeats;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne
	@JoinColumn(name = "DRIVER_ID")
	private TravellerEntity driver;

	@ManyToMany
	@JoinTable(name = "intermediate_point", joinColumns =
	{ @JoinColumn(name = "anouncment_id") }, inverseJoinColumns =
	{ @JoinColumn(name = "city_id") })
	private List<CityEntity> interPoints;

	public static class BusinessAnnouncementBuilder
	{
		private Long idField;

		private final CityEntity startPtField;

		private final CityEntity endPtField;

		private String addressField;

		private final Date departureDateField;

		private Date departureTimeField;

		private VehicleEntity vehicleField;

		private BigDecimal priceField;

		private final Short freeSeatsField;

		private final Status statusField;

		private final TravellerEntity driverField;

		private List<CityEntity> interPtsField;

		public BusinessAnnouncementBuilder(final CityEntity inStartPt,
				final CityEntity inEndPt, final Date inDepDate,
				final Short inSeats, final Status inStatus,
				final TravellerEntity inDriver)
		{
			assert null != inStartPt;
			assert null != inEndPt;
			assert null != inDepDate;
			assert null != inSeats;
			assert null != inStatus;
			assert null != inDriver;

			// TODO deep copy
			startPtField = inStartPt;
			// TODO deep copy
			endPtField = inEndPt;
			departureDateField = DeepCopier.copy(inDepDate);
			freeSeatsField = inSeats;
			statusField = inStatus;
			// TODO deep copy
			driverField = inDriver;
		}

		public BusinessAnnouncementBuilder id(final Long inId)
		{
			idField = inId;
			return this;
		}

		public BusinessAnnouncementBuilder address(final String inAddress)
		{
			addressField = inAddress;
			return this;
		}

		public BusinessAnnouncementBuilder departureTime(final Date inTime)
		{
			departureTimeField = DeepCopier.copy(inTime);
			return this;
		}

		public BusinessAnnouncementBuilder price(final BigDecimal inPrice)
		{
			priceField = DeepCopier.copy(inPrice);
			return this;
		}

		public BusinessAnnouncementBuilder vehicle(final VehicleEntity inVehicle)
		{
			// TODO Deep copy
			vehicleField = inVehicle;
			return this;
		}

		public BusinessAnnouncementBuilder intermediatePoints(
				final List<CityEntity> inPoints)
		{
			interPtsField = DeepCopier.copy(inPoints);
			return this;
		}

		public AnnouncementEntity build()
		{
			return new AnnouncementEntity(this);
		}
	}

	protected AnnouncementEntity()
	{
		// this constructor is needed because of the JPA entity constraints
	}

	private AnnouncementEntity(final BusinessAnnouncementBuilder inBuilder)
	{
		id = inBuilder.idField;
		// TODO deep copy
		startPoint = inBuilder.startPtField;
		// TODO deep copy
		endPoint = inBuilder.endPtField;
		address = inBuilder.addressField;
		departureDate = DeepCopier.copy(inBuilder.departureDateField);
		departureTime = DeepCopier.copy(inBuilder.departureTimeField);
		// TODO deep copy
		vehicle = inBuilder.vehicleField;
		price = DeepCopier.copy(inBuilder.priceField);
		freeSeats = inBuilder.freeSeatsField;
		status = inBuilder.statusField;
		// TODO deep copy
		driver = inBuilder.driverField;
		interPoints = DeepCopier.copy(inBuilder.interPtsField);
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public CityEntity getStartPoint()
	{
		// TODO deep copy
		return startPoint;
	}

	@Override
	public CityEntity getEndPoint()
	{
		// TODO deep copy
		return endPoint;
	}

	@Override
	public String getAddress()
	{
		return address;
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
	public VehicleEntity getVehicle()
	{
		// TODO deep copy
		return vehicle;
	}

	@Override
	public BigDecimal getPrice()
	{
		return DeepCopier.copy(price);
	}

	@Override
	public Short getFreeSeats()
	{
		return freeSeats;
	}

	@Override
	public Status getStatus()
	{
		return status;
	}

	@Override
	public TravellerEntity getDriver()
	{
		// TODO deep copy
		return driver;
	}

	@Override
	public List<CityEntity> getIntermediatePoints()
	{
		return DeepCopier.copy(interPoints);
	}
}
