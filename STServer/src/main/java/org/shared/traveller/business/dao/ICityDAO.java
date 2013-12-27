package org.shared.traveller.business.dao;

import java.util.List;

import org.shared.traveller.business.domain.IPersistentCity;

public interface ICityDAO extends IDAO<IPersistentCity>
{
	IPersistentCity findCityByName(final String inName);

	List<String> findCityNames();
}
