package org.shared.traveller.business.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.shared.traveller.business.domain.jpa.CityEntity;

@Entity(name = "country")
@Table(name = "country")
public class CountryEntity extends AbstractEntity
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -8412293715417104076L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "country")
	private List<CityEntity> cities;

	@Override
	public long getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<CityEntity> getCities()
	{
		return cities;
	}

	public void setCities(List<CityEntity> cities)
	{
		this.cities = cities;
	}

}
