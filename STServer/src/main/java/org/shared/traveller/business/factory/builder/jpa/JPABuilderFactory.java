package org.shared.traveller.business.factory.builder.jpa;

import java.text.MessageFormat;
import java.util.Date;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity;
import org.shared.traveller.business.domain.jpa.NotificationEntity.BusinessNotificationBuilder;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.factory.builder.IBusinessBuilderFactory;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class represents a factory for builder instances. These builder instances
 * are used for the creation of complex domain objects.
 *
 * @author "Ivan Ivanov"
 *
 */
public class JPABuilderFactory implements IBusinessBuilderFactory
{
	private static final String WRONG_DOMAIN_TRAVELLER =
			"The traveller instance {0} is of the wrong domain";

	private static final String WRONG_DOMAIN_ANNOUNCEMENT =
			"The announcement instance {0} is of the wrong domain";

	@Override
	public BusinessNotificationBuilder createNotificationsBuilder(
			final Type inType,
			final IPersistentTraveller inReceiver,
			final IPersistentTraveller inSender,
			final IPersistentAnnouncement inAnnouncement,
			final Date inCreationDate)
			throws IncorrectDomainTypeException
	{
		InstanceAsserter.assertNotNull(inType, "notification's type");
		InstanceAsserter.assertNotNull(inReceiver, "notification's receiver");
		InstanceAsserter.assertNotNull(inSender, "notification's sender");
		InstanceAsserter.assertNotNull(inAnnouncement,
				"notification's announcement");
		InstanceAsserter.assertNotNull(inCreationDate,
				"notification's creation date");

		if (!(inReceiver instanceof TravellerEntity))
		{
			throw new IncorrectDomainTypeException(
					MessageFormat.format(WRONG_DOMAIN_TRAVELLER, inReceiver));
		}

		if (!(inSender instanceof TravellerEntity))
		{
			throw new IncorrectDomainTypeException(
					MessageFormat.format(WRONG_DOMAIN_TRAVELLER, inSender));
		}

		if (!(inAnnouncement instanceof AnnouncementEntity))
		{
			throw new IncorrectDomainTypeException(
					MessageFormat
							.format(WRONG_DOMAIN_ANNOUNCEMENT, inAnnouncement));
		}

		return new BusinessNotificationBuilder(inType,
				(TravellerEntity) inReceiver, (TravellerEntity) inSender,
				(AnnouncementEntity) inAnnouncement, inCreationDate);
	}
}
