package org.shared.traveller.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.shared.traveller.business.domain.jpa.TravellerEntity;

@Entity(name = "tempPassword")
@Table(name = "temp_password")
public class TempPasswordEntity extends AbstractEntity
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 52564408975061771L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name = "temp_password")
	private String tempPassword;

	@ManyToOne
	@JoinColumn(name = "traveller_id")
	private TravellerEntity owner;

	@Override
	public long getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTempPassword()
	{
		return tempPassword;
	}

	public void setTempPassword(String tempPassword)
	{
		this.tempPassword = tempPassword;
	}

	public TravellerEntity getOwner()
	{
		return owner;
	}

	public void setOwner(TravellerEntity owner)
	{
		this.owner = owner;
	}
}
