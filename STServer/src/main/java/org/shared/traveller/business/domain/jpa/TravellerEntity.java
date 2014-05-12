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
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

@Entity(name = "traveller")
@Table(name = "traveller")
@NamedQueries(
{ @NamedQuery(name = "TravellerEntity.findByUsername", query = "SELECT t FROM traveller t WHERE t.username = :username") })
public class TravellerEntity extends AbstractEntity implements
		IPersistentTraveller
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
	private Integer ratingSum;

	@Column(name = "travel_count")
	private String travelCount;

	@Column(name = "SMS_NOTIFICATIONS")
	private boolean smsNotifications;

	@Column(name = "EMAIL_NOTIFICATIONS")
	private boolean emailNotifications;

	@OneToMany(mappedBy = "owner")
	private List<VehicleEntity> vehicles;

	@OneToMany(mappedBy = "receiver")
	private List<NotificationEntity> receivedNotifications;

	@OneToMany(mappedBy = "sender")
	private List<NotificationEntity> sentNotifications;

	/**
	 * The constructor instantiates a new traveller entity
	 */
	public TravellerEntity()
	{
	}

	/**
	 * The constructor instantiates a new traveller entity by copying a given
	 * entity
	 * 
	 * @param inTraveller
	 *            the entity to be copied
	 */
	public TravellerEntity(final TravellerEntity inTraveller)
	{
		InstanceAsserter.assertNotNull(inTraveller, "traveller");

		id = inTraveller.id;
		username = inTraveller.username;
		password = inTraveller.password;
		fbAuthToken = inTraveller.fbAuthToken;
		phoneNumber = inTraveller.phoneNumber;
		email = inTraveller.email;
		firstName = inTraveller.firstName;
		lastName = inTraveller.lastName;
		ratingCount = inTraveller.ratingCount;
		ratingSum = inTraveller.ratingSum;
		travelCount = inTraveller.travelCount;
		// vehicles = DeepCopier.copy(inTraveller.vehicles);
		// receivedNotifications = DeepCopier.copy(
		// inTraveller.receivedNotifications);
		// sentNotifications = DeepCopier.copy(
		// inTraveller.sentNotifications);
		smsNotifications = inTraveller.smsNotifications;
		emailNotifications = inTraveller.emailNotifications;
	}

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

	@Override
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	@Override
	public String getFirstName()
	{
		return firstName;
	}

	@Override
	public String getLastName()
	{
		return lastName;
	}

	@Override
	public Integer getRatingCount()
	{
		return ratingCount;
	}

	@Override
	public Integer getRatingSum()
	{
		return ratingSum;
	}

	@Override
	public String getTravelCount()
	{
		return travelCount;
	}

	@Override
	public List<VehicleEntity> getVehicles()
	{
		return DeepCopier.copy(vehicles);
	}

	@Override
	public List<NotificationEntity> getReceivedNotifications()
	{
		return DeepCopier.copy(receivedNotifications);
	}

	@Override
	public List<NotificationEntity> getSentNotifications()
	{
		return DeepCopier.copy(sentNotifications);
	}

	public void addVehicle(VehicleEntity vehicle)
	{
		if (vehicles == null)
		{
			vehicles = new ArrayList<VehicleEntity>();
		}
		vehicles.add(vehicle);

	}

	@Override
	public boolean getSmsNotifications()
	{
		return smsNotifications;
	}

	@Override
	public boolean getEmailNotifications()
	{
		return emailNotifications;
	}
}
