package org.shared.traveller.business.service;

import java.io.Serializable;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IllegalOperationException;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.rest.domain.RequestList;

/**
 * The interface represents the common functionality for request services
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestService extends Serializable
{
	/**
	 * The methods creates a new request, sent by a specific user for a specific
	 * announcement.
	 * 
	 * @param inAnnouncement
	 *            the announcement for which a new request is created.It may not
	 *            be null.
	 * @param inSender
	 *            the user that sent the request. It may not be null.
	 * 
	 * @throws IncorrectDomainTypeException
	 *             if the announcement or the sender specified are from a wrong
	 *             domain
	 * @throws UnsuccessfulResourceCreationException
	 *             if an error occurs while trying to save the new request
	 */
	void createNewRequest(final IPersistentAnnouncement inAnnouncement,
			final IPersistentTraveller inSender)
			throws DataModificationException;

	/**
	 * The method rejects the specified request
	 * 
	 * @param inRequestId
	 *            the id of the request to be rejected. It may not be null.
	 * 
	 * @throws UnsuccessfulUpdateException
	 *             if a problem occurs while trying to find the request which is
	 *             later to be updated
	 * @throws NonExistingResourceException
	 *             if no such request is found
	 * @throws IllegalOperationException
	 *             if the announcement is no longer active or the request is not
	 *             pending
	 */
	IRequestedAnnouncement reject(final Long inRequestId,
			final Long inDriverId);

	/**
	 * The method accepts the specified request and returns the updated request
	 * information for the announcement to which the accepted request belongs
	 * 
	 * @param inRequestId
	 *            the id of the request to be accepted. It may not be null.
	 * @param inDriverId
	 *            the id of the driver to who the request was sent. It may not
	 *            be null
	 * 
	 * @throws UnsuccessfulUpdateException
	 *             if a problem occurs while trying to find the request which is
	 *             later to be updated
	 * @throws NonExistingResourceException
	 *             if no such request is found
	 * @throws IllegalOperationException
	 *             if there are no free seats for this request, the announcement
	 *             is no longer active or the request is not pending
	 */
	IRequestedAnnouncement accept(final Long inRequestId,
			final Long inDriverId);

	RequestList getUserRequests(final AuthenticatedUser inUser,
			final int inStartIndex, final int inCount);

	Long getUserRequestsCount(final AuthenticatedUser inUser);

	/**
	 * The method loads and returns the request information for the specified
	 * announcement and driver ids
	 * 
	 * @param inAnnouncementId
	 *            the id of the announcement for which request information is
	 *            extracted. It may not be null
	 * @param inDriverId
	 *            the id of the driver for who request information is extracted.
	 *            It may not be null
	 * @return the request information for the specified announcement and driver
	 *         or null if there is none
	 * 
	 * @throws InfoLookupException
	 *             if the request information cannot be loaded due to a problem
	 *             in the search process
	 */
	IRequestedAnnouncement loadRequestInfo(
			final Long inAnnouncementId, final Long inDriverId);
}
