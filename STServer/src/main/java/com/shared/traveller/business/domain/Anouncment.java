package com.shared.traveller.business.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "anouncment")
public class Anouncment extends AbstractEntity
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum Status {

	}

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "start_point_id")
	private City startPoint;

	@OneToOne
	@JoinColumn(name = "end_point_id")
	private City endPoint;

	@Column(name = "address")
	private String address;

	@Column(name = "departure_date")
	@Temporal(TemporalType.DATE)
	private Date departureDate;

	// TODO
	@Column(name = "departure_time")
	private BigDecimal departureTime;

	@OneToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "free_seats")
	private Short freeSeats;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@OneToOne
	@JoinColumn(name = "driver_id")
	private Traveller driver;

	@OneToMany(mappedBy = "anouncment")
	private List<Notification> notifications;

	@ManyToMany
	@JoinTable(name = "intermediate_point", joinColumns =
	{ @JoinColumn(name = "anouncment_id") }, inverseJoinColumns =
	{ @JoinColumn(name = "city_id") })
	private List<City> interPoints;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public City getStartPoint()
	{
		return startPoint;
	}

	public void setStartPoint(City startPoint)
	{
		this.startPoint = startPoint;
	}

	public City getEndPoint()
	{
		return endPoint;
	}

	public void setEndPoint(City endPoint)
	{
		this.endPoint = endPoint;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Date getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(Date departureDate)
	{
		this.departureDate = departureDate;
	}

	public BigDecimal getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(BigDecimal departureTime)
	{
		this.departureTime = departureTime;
	}

	public Vehicle getVehicle()
	{
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Short getFreeSeats()
	{
		return freeSeats;
	}

	public void setFreeSeats(Short freeSeats)
	{
		this.freeSeats = freeSeats;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public Traveller getDriver()
	{
		return driver;
	}

	public void setDriver(Traveller driver)
	{
		this.driver = driver;
	}

	public List<Notification> getNotifications()
	{
		return notifications;
	}

	public void setNotifications(List<Notification> notifications)
	{
		this.notifications = notifications;
	}

	public List<City> getInterPoints()
	{
		return interPoints;
	}

	public void setInterPoints(List<City> interPoints)
	{
		this.interPoints = interPoints;
	}
	
	

}
