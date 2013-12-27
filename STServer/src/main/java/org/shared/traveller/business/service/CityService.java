package org.shared.traveller.business.service;

import java.io.Serializable;

import org.shared.traveller.business.dao.ICityDAO;
import org.shared.traveller.business.domain.IPersistentCity;
import org.springframework.beans.factory.annotation.Autowired;

public class CityService implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3034274861496272438L;

	@Autowired
	private ICityDAO cityDAO;

	public IPersistentCity findCityByName(final String inName)
	{
		return cityDAO.findCityByName(inName);
	}
}
