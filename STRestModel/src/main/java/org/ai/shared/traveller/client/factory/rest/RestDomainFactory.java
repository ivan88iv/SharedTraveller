package org.ai.shared.traveller.client.factory.rest;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.request.rest.PlainRequest;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;

/**
 * The class represents a REST factory for creating simple client instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestDomainFactory implements IDomainFactory
{
	@Override
	public IPlainRequest createRequest(Long inId, RequestStatus inStatus,
			final INotificationTraveller inSender)
	{
		return new PlainRequest(inId, inStatus, inSender);
	}
}
