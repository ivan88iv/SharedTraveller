package org.shared.traveller.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.shared.traveller.business.domain.jpa.AnnouncementEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;

@Entity(name = "notification")
@Table(name = "notification")
public class NotificationEntity extends AbstractEntity
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -5587121471588199943L;

	enum Type {

	}

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "traveller_id")
	private TravellerEntity traveller;

	@ManyToOne
	@JoinColumn(name = "anouncment_id")
	private AnnouncementEntity announcment;

	@Column(name = "type")
	@Enumerated(EnumType.ORDINAL)
	private Type type;

	@Override
	public long getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public TravellerEntity getTraveller()
	{
		return traveller;
	}

	public void setTraveller(TravellerEntity traveller)
	{
		this.traveller = traveller;
	}

	public AnnouncementEntity getAnouncment()
	{
		return announcment;
	}

	public void setAnouncment(AnnouncementEntity anouncment)
	{
		this.announcment = anouncment;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

}
