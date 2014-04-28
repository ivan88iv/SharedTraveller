package org.shared.traveller.business.factory.jpa;

import java.text.MessageFormat;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity;
import org.shared.traveller.business.domain.jpa.RequestEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.factory.IBusinessDomainFactory;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class represents a factory for creating JPA domain instances
 * used in the current application
 *
 * @author "Ivan Ivanov"
 *
 */
public class JPADomainFactory implements IBusinessDomainFactory
{
	private static final String WRONG_SENDER_DOMAIN =
			"The domain of the sender {0} is not correct";

	private static final String WRONG_ANNOUNCEMENT_DOMAIN =
			"The domain of the announcement {0} is not correct";

	@Override
	public IPersistentRequest createRequest(RequestStatus inStatus,
			IPersistentTraveller inTraveller,
			IPersistentAnnouncement inAnnouncement)
	{
		InstanceAsserter.assertNotNull(inStatus, "status");
		InstanceAsserter.assertNotNull(inTraveller, "traveller");
		InstanceAsserter.assertNotNull(inAnnouncement, "announcement");

		if (!(inTraveller instanceof TravellerEntity))
		{
			throw new IncorrectDomainTypeException(
					MessageFormat.format(WRONG_SENDER_DOMAIN, inTraveller));
		}

		if (!(inAnnouncement instanceof AnnouncementEntity))
		{
			throw new IncorrectDomainTypeException(
					MessageFormat.format(WRONG_ANNOUNCEMENT_DOMAIN,
							inAnnouncement));
		}

		return new RequestEntity(RequestStatus.PENDING,
				(TravellerEntity) inTraveller,
				(AnnouncementEntity) inAnnouncement);
	}
}
