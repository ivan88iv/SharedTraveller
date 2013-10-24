package com.shared.traveller.business.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Integer id;

	@Column(name = "name")
	private Integer name;

	@OneToMany(mappedBy = "country")
	private List<City> cities;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getName()
	{
		return name;
	}

	public void setName(Integer name)
	{
		this.name = name;
	}

	public List<City> getCities()
	{
		return cities;
	}

	public void setCities(List<City> cities)
	{
		this.cities = cities;
	}

}
