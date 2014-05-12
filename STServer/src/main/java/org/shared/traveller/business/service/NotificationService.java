package org.shared.traveller.business.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.IGenericNotificationDAO;
import org.shared.traveller.business.dao.INotificationDAO;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.BusinessDomainManager;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.IPersistentNotification.IPersistentNotificationBuilder;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.BusinessException;
import org.shared.traveller.business.exception.IllegalOperationException;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.exception.UnsuccessfulResourceDeletionException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.factory.builder.IBusinessBuilderFactory;
import org.shared.traveller.business.transformer.NotificationTransformer;
import org.shared.traveller.client.domain.INotification;
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

	private static final String EMAIL_NOTIFICATION_FORBIDDEN =
			"The user does not want to be notified via email.";

	private static final String MESSAGE_SEND_PROBLEM =
			"A problem occurred while trying to send an email message.";

	private static final String CONFIG_READ_PROBLEM =
			"A problem occurred while loading the server "
					+ "configuration properties.";

	@Autowired
	private IGenericNotificationDAO genericNotificationDAO;

	@Autowired
	private INotificationDAO notificationDAO;

	@Autowired
	private IRequestDAO requestDAO;

	@Autowired
	private IAnnouncementDAO announcementDAO;

	@Autowired
	private ITravellerDAO travellerDAO;

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
	 * The method loads all notifications for the specified receiver from the
	 * persistent layer
	 * 
	 * @param inUserId
	 *            the is of the receiver. It may not be null.
	 * @return the notification instances that correspond to the specified
	 *         receiver
	 * 
	 * @throws InfoLookupException
	 *             if a problem occurs while trying to load the notifications
	 */
	public List<? extends INotification> loadUserNotifications(
			final Long inUserId)
	{
		List<? extends IPersistentNotification> persistentNotifications = null;

		try
		{
			persistentNotifications = notificationDAO
					.extractUserNotifications(inUserId);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("notifications", "receiver id: "
					+ inUserId);
		}

		List<? extends IPersistentGenericNotification> genericNotifs = null;
		try
		{
			genericNotifs = genericNotificationDAO.getAll();
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("generic notfications", dee);
		}

		final NotificationTransformer transformer =
				new NotificationTransformer(genericNotifs);
		return transformer.transformNotifications(persistentNotifications);
	}

	/**
	 * The method removes all 'new request' and 'declined request' notifications
	 * for the specified driver and announcement
	 * 
	 * @param inDriverId
	 *            the id of the driver for who notifications are removed. It may
	 *            not be null
	 * @param inAnnouncementId
	 *            the id of the announcement for which notifications are
	 *            removed. It may not be null
	 * 
	 * @throws UnsuccessfulResourceDeletionException
	 *             if a problem occurs while trying to remove the notifications
	 */
	public void removeDriverNotifications(final Long inDriverId,
			final Long inAnnouncementId)
	{
		try
		{
			notificationDAO.removeDriverNotifications(inDriverId,
					inAnnouncementId);
		} catch (final DataModificationException dme)
		{
			throw new UnsuccessfulResourceDeletionException("notifications",
					dme);
		}
	}

	/**
	 * The method removes the 'trip cancellation', 'request rejection' and
	 * 'request acceptance' notifications associated with a specific passenger.
	 * 
	 * @param inPassengerId
	 *            the id of the passenger. It may not be null
	 * 
	 * @throws UnsuccessfulResourceDeletionException
	 *             if a problem occurs while trying to remove the notifications
	 */
	public void removePassengerNotifications(final Long inPassengerId)
	{
		try
		{
			notificationDAO.removePassengerNotifications(inPassengerId);
		} catch (final DataModificationException dme)
		{
			throw new UnsuccessfulResourceDeletionException("notifications",
					dme);
		}
	}

	/**
	 * The method sends an email notification to the specified recipient
	 * 
	 * @param inMessage
	 *            the message to be sent.
	 * @param inReceiverId
	 *            the receiver of the notification. It may not be null
	 * 
	 * @throws NonExistingResourceException
	 *             if the specified recipient does not exist
	 * @throws IllegalOperationException
	 *             if the recipient does not allow email notifications
	 * @throws BusinessException
	 *             if a problem occurs while sending the email
	 */
	public void sendEmail(final String inMessage, final Long inReceiverId)
	{
		final IPersistentTraveller receiver = travellerDAO
				.findById(inReceiverId);
		if (null == receiver)
		{
			throw new NonExistingResourceException("email receiver");
		}

		if (!receiver.getEmailNotifications())
		{
			throw new IllegalOperationException(EMAIL_NOTIFICATION_FORBIDDEN);
		}

		final String receiverEmail = receiver.getEmail();
		final InputStream stream = getClass().getResourceAsStream(
				"/config/email.properties");
		Properties properties = new Properties();
		String username = null;
		String password = null;
		String subject = null;

		try
		{
			properties.load(stream);
			username = properties.getProperty("user.name");
			password = properties.getProperty("password");
			subject = properties.getProperty("email.subject");
		} catch (final IOException ioe)
		{
			throw new BusinessException(CONFIG_READ_PROBLEM, ioe);
		} finally
		{
			if (stream != null)
			{
				try
				{
					stream.close();
				} catch (final IOException ioe)
				{
					throw new BusinessException(CONFIG_READ_PROBLEM, ioe);
				}
			}
		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		final String authUsername = username;
		final String authPass = password;

		final Session session = Session.getInstance(props,
				new javax.mail.Authenticator()
				{
					@Override
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(authUsername,
								authPass);
					}
				});

		try
		{
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress(username));
			email.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiverEmail));
			email.setSubject(subject);
			email.setText(inMessage);

			Transport.send(email);
		} catch (MessagingException me)
		{
			throw new BusinessException(MESSAGE_SEND_PROBLEM, me);
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
		final IPersistentNotification notification = builder.build();

		// persist the notification
		try
		{
			notificationDAO.insert(notification);
		} catch (final DataModificationException dme)
		{
			throw new UnsuccessfulResourceCreationException("the notification "
					+ notification, dme);
		}
	}
}
