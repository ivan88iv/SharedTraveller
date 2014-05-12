package org.shared.traveller.business.service.transformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.client.domain.ClientDomainManager;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.stereotype.Service;

/**
 * The service class transforms business domain instances to their respective
 * client counterparts
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Service
public class BusinessDomainTransformerService implements Serializable
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 841293949051729364L;

	/**
	 * The method transforms a persistent layer traveller to a traveller that
	 * has some notification information
	 * 
	 * @param inTraveller
	 *            the persistent traveller to be transformed
	 * @return the transformed traveller instance
	 */
	public INotificationTraveller getNotificationTraveller(
			final IPersistentTraveller inTraveller)
	{
		if (inTraveller == null)
		{
			return null;
		}

		return getNotificationTraveller(inTraveller, ClientDomainManager
				.getInstance().getBuilderFactory());
	}

	/**
	 * The method transforms the persistent layer request to a client request
	 * information instance
	 * 
	 * @param inRequest
	 *            the request to be transformed
	 * @return the transformed instance
	 */
	public IRequestInfo getRequestInformation(final IPersistentRequest inRequest)
	{
		if (null == inRequest)
		{
			return null;
		}

		final IPersistentAnnouncement anno = inRequest.getAnnouncement();
		final IPersistentTraveller persistentDriver =
				inRequest.getAnnouncement().getDriver();
		final IBuilderFactory requestBuilderFactory =
				ClientDomainManager.getInstance().getBuilderFactory();
		final IRequestInfo.IBuilder builder =
				requestBuilderFactory.createRequestInfoBuilder();

		return builder.id(inRequest.getId())
				.fromPoint(anno.getStartPoint().getName())
				.toPoint(anno.getEndPoint().getName())
				.departureDate(anno.getDepartureDate())
				.driver(getNotificationTraveller(persistentDriver,
						requestBuilderFactory))
				.sender(inRequest.getSender().getUsername())
				.status(inRequest.getStatus())
				.build();
	}

	/**
	 * The method transforms the passed persistent announcement to a requested
	 * announcement instance
	 * 
	 * @param inRequestedAnnouncement
	 *            the persistent announcement to be transformed. It may not be
	 *            null
	 * @return the transformed requested announcement instance
	 */
	public IRequestedAnnouncement getRequestedAnnouncement(
			final IPersistentAnnouncement inRequestedAnnouncement)
	{
		InstanceAsserter.assertNotNull(inRequestedAnnouncement, "announcement");

		final List<? extends IPersistentRequest> persistentRequests =
				inRequestedAnnouncement.getRequests();
		final IBuilderFactory builderFactory =
				ClientDomainManager.getInstance().getBuilderFactory();
		final IRequestedAnnouncement.IBuilder announcementBuilder =
				builderFactory.createRequestedAnnouncementBuilder();
		announcementBuilder.id(inRequestedAnnouncement.getId())
				.from(inRequestedAnnouncement.getStartPoint().getName())
				.to(inRequestedAnnouncement.getEndPoint().getName())
				.departureDate(
						inRequestedAnnouncement.getDepartureDate())
				.seats(inRequestedAnnouncement.getFreeSeats())
				.status(inRequestedAnnouncement.getStatus())
				.driver(inRequestedAnnouncement.getDriver().getUsername());

		final IDomainFactory domainFactory = ClientDomainManager.getInstance()
				.getDomainFactory();
		final List<IPlainRequest> plainRequests = new ArrayList<IPlainRequest>();
		for (final IPersistentRequest request : persistentRequests)
		{
			final IPersistentTraveller persistentSender = request.getSender();
			final INotificationTraveller sender = getNotificationTraveller(
					persistentSender, builderFactory);
			plainRequests.add(domainFactory.createRequest(
					request.getId(), request.getStatus(), sender));
		}

		return announcementBuilder.requests(plainRequests).build();
	}

	/**
	 * The method transforms a persistent traveller to a notified client
	 * traveller instance
	 * 
	 * @param inTraveller
	 *            the persistent traveller to be transformed. It may not be null
	 * @param inBuilderFactory
	 *            the builder factory used for the transformation
	 * @return the new transformed client traveller
	 */
	private INotificationTraveller getNotificationTraveller(
			final IPersistentTraveller inTraveller,
			final IBuilderFactory inBuilderFactory)
	{
		final INotificationTraveller.IBuilder travellerBuilder = inBuilderFactory
				.createNotificationTravellerBuilder();
		return travellerBuilder.id(inTraveller.getId())
				.allowEmailNotifications(inTraveller.getEmailNotifications())
				.allowSmsNotifications(inTraveller.getSmsNotifications())
				.email(inTraveller.getEmail())
				.phoneNumber(inTraveller.getPhoneNumber())
				.username(inTraveller.getUsername()).build();
	}
}
