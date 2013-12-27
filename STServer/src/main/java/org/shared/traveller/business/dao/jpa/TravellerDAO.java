package org.shared.traveller.business.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.springframework.stereotype.Repository;

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
	public TravellerEntity findByUsername(String inUsername)
	{
		assert null != inUsername : NULL_USERNAME;

		final TypedQuery<TravellerEntity> query =
				entityManager.createNamedQuery(
						"TravellerEntity.findByUsername",
						TravellerEntity.class);
		query.setParameter("username", inUsername);
		query.setMaxResults(1);
		TravellerEntity traveller = null;
		final List<TravellerEntity> results = query.getResultList();
		if (null != results && !results.isEmpty())
		{
			traveller = results.get(0);
		}

		return traveller;
	}
}
