package org.shared.traveller.business.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.ICityDAO;
import org.shared.traveller.business.domain.IPersistentCity;
import org.shared.traveller.business.domain.jpa.CityEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CityDAO extends AbstractDAO<IPersistentCity> implements ICityDAO
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -412613822870103937L;

	private static final String NULL_CITY_NAME =
			"The name of the city may not be null.";

	@Override
	public CityEntity findCityByName(final String inName)
	{
		assert null != inName : NULL_CITY_NAME;

		final TypedQuery<CityEntity> query = entityManager.createNamedQuery(
				"CityEntity.findCityByName", CityEntity.class);
		query.setParameter("name", inName);
		CityEntity foundCity = null;
		final List<CityEntity> results = query.getResultList();
		if (null != results && !results.isEmpty())
		{
			foundCity = results.get(0);
		}

		return foundCity;
	}

	@Override
	public List<String> findCityNames()
	{
		final TypedQuery<String> query = entityManager.createNamedQuery(
				"CityEntity.getCityNames",
				String.class);
		List<String> names = query.getResultList();
		if (names == null)
		{
			names = new ArrayList<String>();
		}

		return names;
	}
}
