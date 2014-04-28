package org.shared.traveller.business.domain.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;

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
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "country")
	private List<CityEntity> cities;

	@Override
	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public List<CityEntity> getCities()
	{
		return cities;
	}
}
