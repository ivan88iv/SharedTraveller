package org.shared.traveller.client.service.rest;

import java.text.MessageFormat;
import java.util.List;

import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.UnsuccessfulResourceDeletionException;
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

	private static final String NO_EXISTING_USER =
			"No user with id {0} exists.";

	@Autowired
	private NotificationService notificationService;

	/**
	 * The method retrieves the user notifications available for a particular
	 * user and removes them from the server side
	 * 
	 * @param inUserId
	 *            the id of the notifications' receiver
	 * @return the notifications for the specified receiver with
	 *         {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             with {@link HttpStatus#NOT_FOUND} if no user with the
	 *             specified id exists
	 * @throws InternalBusinessException
	 *             with {@link HttpStatus#INTERNAL_SERVER_ERROR} if a problem
	 *             occurs while trying to perform the notifications' extraction
	 *             or deletion
	 */
	@RequestMapping(value = "/all/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<List<? extends INotification>> getUserNotifications(
			@PathVariable(value = "userId") final Long inUserId)
	{
		if (null == inUserId)
		{
			throw new IncorrectInputException(
					MessageFormat.format(NO_EXISTING_USER, inUserId),
					HttpStatus.NOT_FOUND);
		}

		try
		{
			final List<? extends INotification> loadedNotifications =
					notificationService
							.loadAndRemoveUserNotifications(inUserId);
			return new ResponseEntity<List<? extends INotification>>(
					loadedNotifications, HttpStatus.OK);
		} catch (final InfoLookupException |
				UnsuccessfulResourceDeletionException urde)
		{
			throw new InternalBusinessException(MessageFormat.format(
					NOTIFICATION_LOAD_PROBLEM, inUserId), urde);
		}
	}
}
