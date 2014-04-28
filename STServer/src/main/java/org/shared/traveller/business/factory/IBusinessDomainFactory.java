package org.shared.traveller.business.factory;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.client.domain.request.RequestStatus;

/**
 * The class represents a factory for the domain instances used throughout the
 * application
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IBusinessDomainFactory
{
	/**
	 * The method creates a new persistent request
	 *
	 * @param inStatus
	 *            the status of the request. It may not be null.
	 * @param inTraveller
	 *            the traveller associated with the request. It may not be null.
	 * @param inAnnouncement
	 *            the announcement for which the request is sent. It may not be
	 *            null.
	 * @return a new persistent request.
	 *
	 * @throws IncorrectDomainTypeException
	 *             when the traveller or the announcement specified are from the
	 *             wrong domain
	 */
	IPersistentRequest createRequest(final RequestStatus inStatus,
			final IPersistentTraveller inTraveller,
			final IPersistentAnnouncement inAnnouncement);
}
