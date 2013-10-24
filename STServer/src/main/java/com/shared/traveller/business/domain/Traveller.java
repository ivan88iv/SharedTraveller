package com.shared.traveller.business.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "traveller")
public class Traveller extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "facebook_auth_token")
	private String fbAuthToken;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "rating_count")
	private Integer ratingCount;

	@Column(name = "rating_sum")
	private Integer rating_sum;

	@Column(name = "travel_count")
	private String travelCount;

	@OneToMany(mappedBy = "owner")
	private List<Vehicle> vehicles;

	@OneToMany(mappedBy = "traveller")
	private List<Notification> notifications;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFbAuthToken()
	{
		return fbAuthToken;
	}

	public void setFbAuthToken(String fbAuthToken)
	{
		this.fbAuthToken = fbAuthToken;
	}

	public String getPhoneNUmber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Integer getRatingCount()
	{
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount)
	{
		this.ratingCount = ratingCount;
	}

	public Integer getRating_sum()
	{
		return rating_sum;
	}

	public void setRating_sum(Integer rating_sum)
	{
		this.rating_sum = rating_sum;
	}

	public String getTravelCount()
	{
		return travelCount;
	}

	public void setTravelCount(String travelCount)
	{
		this.travelCount = travelCount;
	}

	public List<Vehicle> getVehicles()
	{
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
	}

	public List<Notification> getNotifications()
	{
		return notifications;
	}

	public void setNotifications(List<Notification> notifications)
	{
		this.notifications = notifications;
	}

	public void addVehicle(Vehicle vehicle)
	{
		if (vehicles == null)
		{
			vehicles = new ArrayList<>();
		}
		vehicles.add(vehicle);

	}

}
