package org.shared.traveller.business.service;

import java.io.Serializable;

import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class performs business actions over traveller instances
 *
 * @author "Ivan Ivanov"
 *
 */
@Service
public class TravellerService implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 6098801418782520802L;

	private static final String NULL_USERNAME =
			"The username may not be null.";

	@Autowired
	private ITravellerDAO travellerDAO;

	/**
	 * The method finds a traveller by his/her user name
	 *
	 * @param inUsername the user name of the traveller.
	 * It must not be null.
	 *
	 * @return the found traveller
	 *
	 * @throws DataExtractionException if a problem occurs
	 * during the extraction process
	 */
	public IPersistentTraveller findByUsername(final String inUsername)
		throws DataExtractionException {
		assert null != inUsername : NULL_USERNAME;

		return travellerDAO.findByUsername(inUsername);
	}
}

