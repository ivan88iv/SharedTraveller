package org.shared.traveller.business.domain.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentTraveller;

@Entity(name = "traveller")
@Table(name = "traveller")
@NamedQueries(
{ @NamedQuery(name = "TravellerEntity.findByUsername", query = "SELECT t FROM traveller t WHERE t.username = :username") })
public class TravellerEntity extends AbstractEntity implements IPersistentTraveller
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -2721474209361437647L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Long id;

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
	private List<VehicleEntity> vehicles;

	@OneToMany(mappedBy = "traveller")
	private List<NotificationEntity> notifications;

	@Override
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String getFbAuthToken()
	{
		return fbAuthToken;
	}

	public void setFbAuthToken(String fbAuthToken)
	{
		this.fbAuthToken = fbAuthToken;
	}

	@Override
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	@Override
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@Override
	public Integer getRatingCount()
	{
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount)
	{
		this.ratingCount = ratingCount;
	}

	@Override
	public Integer getRating_sum()
	{
		return rating_sum;
	}

	public void setRating_sum(Integer rating_sum)
	{
		this.rating_sum = rating_sum;
	}

	@Override
	public String getTravelCount()
	{
		return travelCount;
	}

	public void setTravelCount(String travelCount)
	{
		this.travelCount = travelCount;
	}

	@Override
	public List<VehicleEntity> getVehicles()
	{
		return vehicles;
	}

	public void setVehicles(List<VehicleEntity> vehicles)
	{
		this.vehicles = vehicles;
	}

	@Override
	public List<NotificationEntity> getNotifications()
	{
		return notifications;
	}

	public void setNotifications(List<NotificationEntity> notifications)
	{
		this.notifications = notifications;
	}

	public void addVehicle(VehicleEntity vehicle)
	{
		if (vehicles == null)
		{
			vehicles = new ArrayList<VehicleEntity>();
		}
		vehicles.add(vehicle);

	}
}
