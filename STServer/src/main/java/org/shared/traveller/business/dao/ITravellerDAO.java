package org.shared.traveller.business.dao;

import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.persistence.DataExtractionException;

public interface ITravellerDAO extends IDAO<IPersistentTraveller>
{
	/**
	 * The method finds the traveller by his/her user name
	 *
	 * @param inUsername the user name of the traveller
	 * @return the traveller with the provided user name
	 * @throws DataExtractionException if an error occurs while trying
	 * to find the given traveller
	 */
	IPersistentTraveller findByUsername(final String inUsername)
		throws DataExtractionException;
}
