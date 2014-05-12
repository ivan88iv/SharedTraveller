package org.ai.shared.traveller.client.factory;

import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;

/**
 * The interface represents the common functionality a factory for creating
 * simple client domain instances must possess
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IDomainFactory
{
	/**
	 * The method creates a new plain request instance
	 * 
	 * @param inId
	 *            the id of the new request to be created.
	 * @param inStatus
	 *            the status of the new request
	 * @param inSender
	 *            the sender associated with the new request
	 * 
	 * @return the newly created request
	 */
	IPlainRequest createRequest(final Long inId, final RequestStatus inStatus,
			final INotificationTraveller inSender);
}
