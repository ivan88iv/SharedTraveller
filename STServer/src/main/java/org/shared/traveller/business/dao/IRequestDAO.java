package org.shared.traveller.business.dao;

import java.util.Date;
import java.util.List;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
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

	/**
	 * The method loads all the requests for the specified
	 * announcement information
	 *
	 * @param inStartPt the start settlement point for the announcement.
	 * It may not be null.
	 * @param inEndPt the end settlement point for the announcement.
	 * It may not be null.
	 * @param inDepDate the departure date for the announcement.
	 * It may not be null.
	 * @param inDriverUsrname the user name for the announcement.
	 * It may not be null.
	 * @return the requests for the specified announcement information.
	 * It may not be null.
	 * @throws DataExtractionException if a problem occurs while
	 * extracting the requests
	 */
	List<? extends IPersistentRequest> loadRequests(final String inStartPt,
			final String inEndPt, final Date inDepDate,
			final String inDriverUsrname);

	/**
	 * The method finds and returns a request by the user name of
	 * the driver and the identification of the request
	 *
	 * @param inDriver the user name of the driver to whom the
	 * searched request was sent. It may not be null.
	 * @param inRequestId the identification of the request. It may
	 * not be null
	 * @return the request with the provided identification that belongs
	 * to the specified driver or null if none is found
	 *
	 * @throws DataExtractionException if a problem occurs while trying
	 * to extract the requests
	 */
	IPersistentRequest findRequest(final String inDriver,
			final Long inRequestId);
}
