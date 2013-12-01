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

@Entity
@Table(name = "notification")
public class Notification extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	enum Type {

	}

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "traveller_id")
	private Traveller traveller;

	@ManyToOne
	@JoinColumn(name = "anouncment_id")
	private AnnouncementEntity anouncment;

	@Column(name = "type")
	@Enumerated(EnumType.ORDINAL)
	private Type type;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Traveller getTraveller()
	{
		return traveller;
	}

	public void setTraveller(Traveller traveller)
	{
		this.traveller = traveller;
	}

	public AnnouncementEntity getAnouncment()
	{
		return anouncment;
	}

	public void setAnouncment(AnnouncementEntity anouncment)
	{
		this.anouncment = anouncment;
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
