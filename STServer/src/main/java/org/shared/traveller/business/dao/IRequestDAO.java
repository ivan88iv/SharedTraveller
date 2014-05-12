package org.shared.traveller.business.dao;

import java.util.List;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.jpa.RequestEntity;
import org.shared.traveller.business.exception.persistence.DataExtractionException;

/**
 * The class is responsible for the data access operations with regard to the
 * persistent request instances used throughout the application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestDAO extends IDAO<IPersistentRequest>
{
	/**
	 * The method loads all requests which have been made for the current
	 * announcement and driver
	 * 
	 * @param inAnnouncementId
	 *            the id of the announcement whose requests are to be loaded. It
	 *            may not be null
	 * @param inDriverId
	 *            the id the driver for which requests are loaded. It may not be
	 *            null
	 * @return the loaded announcement requests
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while extracting the requests
	 */
	List<? extends IPersistentRequest> loadRequests(
			final Long inAnnouncementId, final Long inDriverId);

	/**
	 * The method finds and returns a request by the user name of the driver and
	 * the identification of the request
	 * 
	 * @param inDriver
	 *            the id of the driver to whom the searched request was sent. It
	 *            may not be null.
	 * @param inRequestId
	 *            the identification of the request. It may not be null
	 * @return the request with the provided identification that belongs to the
	 *         specified driver or null if none is found
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while trying to extract the requests
	 */
	IPersistentRequest findRequest(final Long inDriverId, final Long inRequestId);

	Long findUserRequestsCount(final AuthenticatedUser inUser);

	List<RequestEntity> findUserRequests(final AuthenticatedUser inUser,
			final int inStartIndex, final int inMaxResult);
}
