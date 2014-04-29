package org.shared.traveller.client.service.rest;

import java.text.MessageFormat;

import org.shared.traveller.business.exception.IncorrectInputException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.InternalBusinessException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.service.NotificationService;
import org.shared.traveller.client.domain.INotification.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The class is responsible for conducting the trip related REST service actions
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Controller
@RequestMapping("/trip")
public class RestTripService
{
	private static final String WRONG_ANNOUNCEMENT_ID =
			"The specified announcement id {0} is not correct.";

	private static final String CANCEL_TRIP_PROBLEM =
			"A problem occurred while trying to cancel the trip for the announcemnt "
					+ "with id {0}.";

	@Autowired
	private NotificationService notificationService;

	/**
	 * The method cancels a trip designated by an announcement id
	 * 
	 * @param inAnnouncementId
	 *            the id of the announcement for the trip to be cancelled
	 * @return an empty response with an {@link HttpStatus#OK} if the trip was
	 *         cancelled successfully, {@link HttpStatus#NOT_FOUND} if no
	 *         announcement with the specified id exists or the id is null and
	 *         {@link HttpStatus#INTERNAL_SERVER_ERROR} if a problem occurred in
	 *         the cancellation process
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ResponseEntity<Void> cancelTrip(
			@RequestParam(value = "announcementId") final Long inAnnouncementId)
	{
		try
		{
			// TODO implement the logic on canceling a trip
			notificationService.createAnnouncementNotification(
					inAnnouncementId, Type.TRIP_CANCELLATION);
		} catch (final NonExistingResourceException nere)
		{
			throw new IncorrectInputException(MessageFormat.format(
					WRONG_ANNOUNCEMENT_ID, inAnnouncementId), nere,
					HttpStatus.NOT_FOUND);
		} catch (final InfoLookupException
				| UnsuccessfulResourceCreationException urce)
		{
			throw new InternalBusinessException(
					MessageFormat.format(CANCEL_TRIP_PROBLEM, inAnnouncementId),
					urce);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
