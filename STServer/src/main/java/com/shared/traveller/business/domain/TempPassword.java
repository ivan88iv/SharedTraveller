package com.shared.traveller.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "temp_password")
public class TempPassword extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "temp_password")
	private String tempPassword;

	@ManyToOne
	@JoinColumn(name = "traveller_id")
	private Traveller owner;

	public Integer getId()
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

	public Traveller getOwner()
	{
		return owner;
	}

	public void setOwner(Traveller owner)
	{
		this.owner = owner;
	}

}
