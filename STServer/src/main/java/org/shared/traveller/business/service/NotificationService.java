package org.shared.traveller.business.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.IGenericNotificationDAO;
import org.shared.traveller.business.dao.INotificationDAO;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.domain.BusinessDomainManager;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.IPersistentNotification.IPersistentNotificationBuilder;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.visitor.impl.NotificationDescriptor;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.factory.builder.IBusinessBuilderFactory;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class is responsible for performing business actions regarding the
 * notification business instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
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

	@Autowired
	private IRequestDAO requestDAO;

	@Autowired
	private IAnnouncementDAO announcementDAO;

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
	 *             if a problem occurs while trying to load resources for the
	 *             proper notification's initialization
	 * @throws UnsuccessfulResourceCreationException
	 *             if no notification could be persisted because of persistent
	 *             layer problem
	 */
	public void createNewNotification(final Type inType,
			final IPersistentTraveller inSender,
			final IPersistentTraveller inReceiver,
			final IPersistentAnnouncement inAnnouncement)
	{
		persistNewNotification(inType, inSender, inReceiver, inAnnouncement);
	}

	/**
	 * The method creates and persists a new notification with the selected type
	 * for a travel request with the specified id
	 * 
	 * @param inRequestId
	 *            the id of the request
	 * @param inNotificationType
	 *            the type of the notification to be created
	 * 
	 * @throws InfoLookupException
	 *             if a problem occurs while trying to load resources for the
	 *             proper notification's initialization
	 * @throws UnsuccessfulResourceCreationException
	 *             if no notification could be persisted because of persistent
	 *             layer problem
	 * @throws NonExistingResourceException
	 *             if no request was found for the provided id or the request id
	 *             is null
	 */
	public void createNewRequestNotification(final Long inRequestId,
			final Type inNotificationType)
	{
		if (null == inRequestId)
		{
			throw new NonExistingResourceException("request with id "
					+ inRequestId);
		}

		IPersistentRequest request = null;
		try
		{
			request = requestDAO.findById(inRequestId);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("request", "with id " + inRequestId,
					dee);
		}

		if (null == request)
		{
			throw new NonExistingResourceException("request with id "
					+ inRequestId);
		}

		final IPersistentAnnouncement announcement = request.getAnnouncement();
		final IPersistentTraveller sender = announcement.getDriver();
		final IPersistentTraveller receiver = request.getSender();
		persistNewNotification(inNotificationType, sender, receiver,
				announcement);
	}

	/**
	 * The method creates a new notification to everyone who has sent a request
	 * about the specified trip
	 * 
	 * @param inAnnouncementId
	 *            the id of the announcement for the trip
	 * @param inNotificationType
	 *            the type of the notification sent
	 * 
	 * @throws NonExistingResourceException
	 *             if the announcement id is null or there is no announcement
	 *             with the specified id
	 * @throws InfoLookupException
	 *             if a problem occurs while trying to load resources for the
	 *             proper notification's initialization
	 * @throws UnsuccessfulResourceCreationException
	 *             - if no notification could be persisted because of persistent
	 *             layer problem
	 */
	public void createAnnouncementNotification(final Long inAnnouncementId,
			final Type inNotificationType)
	{
		if (null == inAnnouncementId)
		{
			throw new NonExistingResourceException("announcement with id "
					+ inAnnouncementId);
		}

		IPersistentAnnouncement announcement = null;
		try
		{
			announcement = announcementDAO.
					loadAnnouncementWithRequests(inAnnouncementId);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("announcement", "id: "
					+ inAnnouncementId, dee);
		}

		if (null == announcement)
		{
			throw new NonExistingResourceException("announcement with id "
					+ inAnnouncementId);
		}

		final IPersistentTraveller notificaitonSender = announcement
				.getDriver();
		final List<? extends IPersistentRequest> requests =
				announcement.getRequests();

		for (final IPersistentRequest request : requests)
		{
			final IPersistentTraveller notificationReceiver = request
					.getSender();
			persistNewNotification(inNotificationType, notificaitonSender,
					notificationReceiver, announcement);
		}
	}

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
	 *             if a problem occurs while trying to load resources for the
	 *             proper notification's initialization
	 * @throws UnsuccessfulResourceCreationException
	 *             if no notification could be persisted because of persistent
	 *             layer problem
	 */
	private void persistNewNotification(final Type inType,
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
			throw new UnsuccessfulResourceCreationException("the notification "
					+ realNotification.toString(), dme);
		}
	}
}
