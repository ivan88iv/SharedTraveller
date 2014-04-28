package org.shared.traveller.business.service;

import java.io.Serializable;
import java.util.Date;

import org.shared.traveller.business.dao.IGenericNotificationDAO;
import org.shared.traveller.business.dao.INotificationDAO;
import org.shared.traveller.business.domain.BusinessDomainManager;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.IPersistentNotification.IPersistentNotificationBuilder;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.visitor.impl.NotificationDescriptor;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.factory.builder.IBusinessBuilderFactory;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 4716362544914229015L;

	@Autowired
	private IGenericNotificationDAO genericNotificationDAO;

	@Autowired
	private INotificationDAO notificationDAO;

	/**
	 * The method creates and persists a new notification for the specified
	 * type, sender and receiver
	 *
	 * @param inType
	 *            the type of the notification to be created. It may not be null
	 * @param inSender
	 *            the sender of the notification to be created. It may not be
	 *            null.
	 * @param inReceiver
	 *            the receiver of the notification to be created. It may not be
	 *            null.
	 * @param inAnnouncement
	 *            the announcement of the notification to be created. It may not
	 *            be null.
	 *
	 * @throws IncorrectDomainTypeException
	 *             if the sender, receiver or announcement are from a wrong
	 *             business domain
	 * @throws InfoLookupException
	 *             if a problem occurs while trying to load the description
	 *             template for the current notification's type
	 * @throws UnsuccessfulUpdateException
	 *             if no notification could be persisted because of persistent
	 *             layer problem
	 */
	public void createNewNotification(final Type inType,
			final IPersistentTraveller inSender,
			final IPersistentTraveller inReceiver,
			final IPersistentAnnouncement inAnnouncement)
	{
		InstanceAsserter.assertNotNull(inType, "notification's type");
		InstanceAsserter.assertNotNull(inSender, "notification's sender");
		InstanceAsserter.assertNotNull(inSender, "notification's receiver");

		// build the new notification
		final IBusinessBuilderFactory builderFactory =
				BusinessDomainManager.getInstance().getBuilderFactory();
		final IPersistentNotificationBuilder builder =
				builderFactory.createNotificationsBuilder(inType, inReceiver,
						inSender, inAnnouncement, new Date());
		final IPersistentNotification descriptionlessNotf = builder.build();
		// get the description template from the persistent layer
		String notificationTemplate = null;
		try
		{
			notificationTemplate =
					genericNotificationDAO.findNotificationTemplate(inType);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException(
					"a notification's description template",
					"notificaion's type: " + inType, dee);
		}
		// construct the notification's description
		final NotificationDescriptor descriptor = new NotificationDescriptor(
				notificationTemplate);
		descriptionlessNotf.accept(descriptor);
		final String description = descriptor.getDescription();
		final IPersistentNotification realNotification = builder
				.description(description).build();
		// persist the notification
		try
		{
			notificationDAO.insert(realNotification);
		} catch (final DataModificationException dme)
		{
			throw new UnsuccessfulUpdateException(
					realNotification.toString(), dme);
		}
	}
}
