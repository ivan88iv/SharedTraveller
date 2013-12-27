package org.shared.traveller.business.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.CountryEntity;
import org.shared.traveller.business.domain.IPersistentCity;

@Entity(name = "city")
@Table(name = "city")
@NamedQueries(
{
		@NamedQuery(name = "CityEntity.findCityByName",
				query = "SELECT c FROM city c WHERE c.name = :name")
})
public class CityEntity extends AbstractEntity implements IPersistentCity
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -7940217241918563862L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

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

	public CountryEntity getCountry()
	{
		return country;
	}

	public void setCountry(CountryEntity country)
	{
		this.country = country;
	}

}
