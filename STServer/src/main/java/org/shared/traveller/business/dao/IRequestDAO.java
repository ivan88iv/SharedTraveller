package org.shared.traveller.business.dao;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.persistence.DataModificationException;

/**
 * The class is responsible for the data access operations with
 * regard to the persistent request instances used throughout the
 * application
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IRequestDAO extends IDAO<IPersistentRequest>
{
	/**
	 * The method creates a new persistent request instance
	 * and saves it.
	 *
	 * @param inSender the sender of the request.
	 * 		It may not be null.
	 * @param inAnnouncement the announcement for
	 * which a request is sent. It may not be null.
	 *
	 * @throws DataModificationException if an error
	 * occurs while trying to save the new request
	 */
	void saveNewRequest(final IPersistentTraveller inSender,
			final IPersistentAnnouncement inAnnouncement)
	throws DataModificationException;
}
