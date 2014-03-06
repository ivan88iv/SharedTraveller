package org.shared.traveller.business.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.springframework.stereotype.Repository;

/**
 * The class is responsible for data access operations
 * regarding JPA traveller instances
 *
 * @author "Ivan Ivanov"
 *
 */
@Repository
public class TravellerDAO extends AbstractDAO<IPersistentTraveller>
		implements ITravellerDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -6328658681129541724L;

	private static final String NULL_USERNAME = "The user name cannot be null!";

	@Override
	public TravellerEntity findByUsername(final String inUsername)
		throws DataExtractionException
	{
		assert null != inUsername : NULL_USERNAME;

		final DataExtractor<TravellerEntity> extractor =
				new DataExtractor<TravellerEntity>()
				{
					@Override
					protected void prepareQuery(
							TypedQuery<TravellerEntity> inQuery)
					{
						inQuery.setParameter("username", inUsername)
							.setMaxResults(1);
					}
				};

		final List<TravellerEntity> results =
				extractor.execute("TravellerEntity.findByUsername",
				entityManager, TravellerEntity.class,
				"An error occurred while trying to extract a traveller.");

		TravellerEntity traveller = null;
		if (!results.isEmpty())
		{
			traveller = results.get(0);
		}

		return traveller;
	}

	@Override
	protected Class<? extends IPersistentTraveller> getEntityClass()
	{
		return TravellerEntity.class;
	}
}
