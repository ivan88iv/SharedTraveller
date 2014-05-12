package org.shared.traveller.client.service.rest;

import java.text.MessageFormat;
import java.util.List;

import org.shared.traveller.business.exception.BusinessException;
import org.shared.traveller.business.exception.IllegalOperationException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.service.NotificationService;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.exception.rest.IncorrectInputException;
import org.shared.traveller.client.exception.rest.InternalBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The class represents the REST service calls available with regard to
 * notifications
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Component
@RequestMapping("/notification")
public class RestNotificationService
{
	private static final String NOTIFICATION_LOAD_PROBLEM =
			"A problem occurred while trying to find notficaitons for user "
					+ "with id {0}.";

	private static final String NULL_USER_ID =
			"The user's id may not be null.";

	private static final String INCORRE_EMAIL_INPUT =
			"Please validate that the message to be sent and the message receiver "
					+ "are not null.";

	private static final String NO_RECIPIENT =
			"No email recipient could be found.";

	private static final String MESSAGE_SENDING_NOT_ALLOWED =
			"Could not send the email notification because the recipient "
					+ "does not allow it.";

	private static final String MESSAGE_SEND_PROBLEM =
			"Could not send email notification because of a server problem.";

	@Autowired
	private NotificationService notificationService;

	/**
	 * The method retrieves the user notifications available for a particular
	 * user
	 * 
	 * @param inUserId
	 *            the id of the notifications' receiver
	 * @return the notifications for the specified receiver with
	 *         {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             with {@link HttpStatus#NOT_FOUND} if the user's id is null
	 * @throws InternalBusinessException
	 *             with {@link HttpStatus#INTERNAL_SERVER_ERROR} if a problem
	 *             occurs while trying to perform the notifications' extraction
	 */
	@RequestMapping(value = "/all/{userId}", method = RequestMethod.POST)
	public ResponseEntity<List<? extends INotification>> getUserNotifications(
			@PathVariable(value = "userId") final Long inUserId)
	{
		if (null == inUserId)
		{
			throw new IncorrectInputException(
					MessageFormat.format(NULL_USER_ID, inUserId),
					HttpStatus.NOT_FOUND);
		}

		try
		{
			final List<? extends INotification> loadedNotifications =
					notificationService.loadUserNotifications(inUserId);
			return new ResponseEntity<List<? extends INotification>>(
					loadedNotifications, HttpStatus.OK);
		} catch (final InfoLookupException ile)
		{
			throw new InternalBusinessException(MessageFormat.format(
					NOTIFICATION_LOAD_PROBLEM, inUserId), ile);
		}
	}

	/**
	 * The method sends email notifications to the specified recipient
	 * 
	 * @param inMessage
	 *            the message to be sent
	 * @param inReceiverId
	 *            the id of the message receiver
	 * @return a {@link HttpStatus#NO_CONTENT} response
	 * 
	 * @throws IncorrectInputException
	 *             with {@link HttpStatus#NOT_FOUND} when the message or
	 *             recipient's id are null, if no recipient exists with the
	 *             specified id; with {@link HttpStatus#INTERNAL_SERVER_ERROR}
	 *             if the receiver has specified they do not want to receive
	 *             notifications via email
	 * @throws InternalBusinessException
	 *             if a problem occurs while sending the message
	 */
	@RequestMapping(value = "/send/email", method = RequestMethod.POST)
	public ResponseEntity<Void> sendEmailNotification(
			@RequestParam(value = "message") final String inMessage,
			@RequestParam(value = "receiverId") final Long inReceiverId)
	{
		if (null == inMessage || null == inReceiverId)
		{
			throw new IncorrectInputException(INCORRE_EMAIL_INPUT,
					HttpStatus.NOT_FOUND);
		}

		try
		{
			notificationService.sendEmail(inMessage, inReceiverId);
		} catch (final NonExistingResourceException nere)
		{
			throw new IncorrectInputException(NO_RECIPIENT, nere,
					HttpStatus.NOT_FOUND);
		} catch (final IllegalOperationException ioe)
		{
			throw new IncorrectInputException(MESSAGE_SENDING_NOT_ALLOWED, ioe,
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (final BusinessException be)
		{
			throw new InternalBusinessException(MESSAGE_SEND_PROBLEM, be);
		}

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
