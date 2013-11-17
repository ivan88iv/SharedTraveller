package org.shared.traveller.business.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vehicle")
public class Vehicle extends AbstractEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "make")
	private String make;

	@Column(name = "model")
	private String model;

	@Column(name = "year_of_production")
	@Temporal(TemporalType.DATE)
	private Date yearOfProduction;

	@Column(name = "registration_number")
	private String regNumber;

	@Column(name = "color")
	private String color;

	@Column(name = "seats")
	private Short seats;

	@Column(name = "description")
	private String desc;

	@Column(name = "ccu")
	private Boolean ccu;

	@Column(name = "air_bag")
	private Boolean airbag;

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

	public String getMake()
	{
		return make;
	}

	public void setMake(String make)
	{
		this.make = make;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public Date getYearOfProduction()
	{
		return yearOfProduction;
	}

	public void setYearOfProduction(Date yearOfProduction)
	{
		this.yearOfProduction = yearOfProduction;
	}

	public String getRegNumber()
	{
		return regNumber;
	}

	public void setRegNumber(String regNumber)
	{
		this.regNumber = regNumber;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public Short getSeats()
	{
		return seats;
	}

	public void setSeats(Short seats)
	{
		this.seats = seats;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public Boolean getCcu()
	{
		return ccu;
	}

	public void setCcu(Boolean ccu)
	{
		this.ccu = ccu;
	}

	public Boolean getAirbag()
	{
		return airbag;
	}

	public void setAirbag(Boolean airbag)
	{
		this.airbag = airbag;
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
