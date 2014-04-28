package org.shared.traveller.client.service.rest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IllegalUpdateOperationException;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.IncorrectInputException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.InternalBusinessException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.business.service.NotificationService;
import org.shared.traveller.business.service.RequestService;
import org.shared.traveller.business.service.TravellerService;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.rest.RequestInfo;
import org.shared.traveller.rest.domain.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/request")
public class RestRequestService
{
	private static final String NULL_NEW_REQUEST_EVENT =
			"The event for creating a new request may not be null.";

	private static final String TRAVEL_REQUEST_SEND_PROBLEM =
			"Could not send a new travel request: {0}.";

	private static final String INCORRECT_REQUEST_INPUT =
			"The request input is not correct: {0}.";

	private static final String REQUEST_EXTRACTION_PROBLEM =
			"Could not extract requests for announcement info: "
					+ "start settlement {0},"
					+ "end settlement {1},"
					+ "departure date {2}, driver {3}.";

	private static final String NO_REQUEST_FOUND =
			"The request with id {0} does not exist.";

	private static final String REQUEST_ACCEPTANCE_PROBLEM =
			"A problem occurred while trying to accept the";

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private TravellerService travellerService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private NotificationService notificationService;

	/**
	 *
	 * The method is a rest service method that is responsible for the creation
	 * of a new request business object. It uses a special event object for this
	 * purpose.
	 *
	 * @param inRequest
	 *            the {@link RequestInfo} instance used for the particular
	 *            request creation
	 * @return {@link HttpStatus#NOT_FOUND} status if the announcement or the
	 *         traveller specified by the event do not exist and
	 *         {@link HttpStatus#CREATED} if the request instance was
	 *         successfully created.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.PUT)
	public ResponseEntity<Void> sendTravelRequest(
			@RequestBody final RequestInfo inRequest)
	{
		if (null == inRequest)
		{
			throw new IncorrectInputException(NULL_NEW_REQUEST_EVENT,
					HttpStatus.NOT_FOUND);
		}

		IPersistentAnnouncement loadedAnnouncement = null;
		IPersistentTraveller sender = null;

		try
		{
			loadedAnnouncement = announcementService.loadAnnouncement(
					inRequest.getFromPoint(), inRequest.getToPoint(),
					inRequest.getDepartureDate(), inRequest.getDriver());
			sender = travellerService.findByUsername(inRequest.getSender());

		} catch (final DataExtractionException dee)
		{
			throw new InternalBusinessException(MessageFormat.format(
					TRAVEL_REQUEST_SEND_PROBLEM, inRequest), dee);
		}

		if (null == loadedAnnouncement || null == sender)
		{
			throw new IncorrectInputException(MessageFormat.format(
					INCORRECT_REQUEST_INPUT, inRequest),
					HttpStatus.NOT_FOUND);
		}

		try
		{
			requestService.createNewRequest(loadedAnnouncement, sender);
			notificationService.createNewNotification(Type.NEW_REQUEST,
					sender, loadedAnnouncement.getDriver(), loadedAnnouncement);
		} catch (final IncorrectDomainTypeException |
				DataModificationException dme)
		{
			throw new InternalBusinessException(MessageFormat.format(
					TRAVEL_REQUEST_SEND_PROBLEM, inRequest), dme);
		}

		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}

	/**
	 *
	 * The method finds and returns all the request information that has been
	 * generated for the specified announcement
	 *
	 * @param inAnnouncement
	 *            the announcement instance for which the request information is
	 *            returned.
	 * @return the request information instances related to the specified
	 *         announcement
	 */

	@RequestMapping(value = "/announcement/all", method = RequestMethod.GET, params =
	{ "from", "to", "departureDate", "driver" })
	public ResponseEntity<List<? extends IRequestInfo>> getRequests(
			@RequestParam(value = "from") final String inFrom,
			@RequestParam(value = "to") final String inTo,

			@RequestParam(value = "departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date inDepartureDate,
			@RequestParam(value = "driver") final String inDriverUsername)
	{

		List<? extends IRequestInfo> requestInfos = new ArrayList<>();

		try
		{

			requestInfos = requestService.loadRequests(inFrom, inTo,
					inDepartureDate, inDriverUsername);
		} catch (final InfoLookupException ile)
		{

			throw new InternalBusinessException(MessageFormat.format(
					REQUEST_EXTRACTION_PROBLEM, inFrom, inTo,
					inDepartureDate, inDriverUsername), ile);
		}

		return new ResponseEntity<List<? extends IRequestInfo>>(requestInfos,
				HttpStatus.OK);
	}

	/**
	 * The method rejects the provided request
	 *
	 *
	 * @param inRequestId
	 *            the id of the request to be rejected
	 * @return an empty result
	 *
	 * @throws IncorrectInputException
	 *             if the specified request does not exist
	 * @throws InternalBusinessException
	 *             if a problem occurs while trying to perform the status change
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ResponseEntity<Void> rejectRequest(
			@RequestParam(value = "id") final Long inRequestId)
	{
		return changeRequestStatus(inRequestId, RequestStatus.REJECTED);
	}

	/**
	 * The method accepts the provided request
	 *
	 *
	 * @param inRequestId
	 *            the id of the request to be accepted
	 * @return an empty result
	 * @throws IncorrectInputException
	 *             if the specified request does not exist
	 * @throws InternalBusinessException
	 *             if a problem occurs while trying to perform the status change
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public ResponseEntity<Void> acceptRequest(
			@RequestParam(value = "id") final Long inRequestId)
	{
		return changeRequestStatus(inRequestId, RequestStatus.APPROVED);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<RequestList> getUserRequests(
			@RequestParam(value = "start") final Long inStartIndex,
			@RequestParam(value = "count") final Long inCount)
	{
		// TODO remove when authentication is implemented
		AuthenticatedUser inUser = new AuthenticatedUser(1L, "username");
		RequestList userRequests = requestService.getUserRequests(inUser,
				inStartIndex.intValue(), inCount.intValue());
		return new ResponseEntity<RequestList>(userRequests, HttpStatus.OK);
	}

	/**
	 * The method changes the status of the provided request
	 *
	 *
	 * @param inRequestId
	 *            the id of the request which status is to be changed
	 * @param inNewStatus
	 *            the new status to use
	 * @return the response entity returned from the corresponding REST service
	 *         that uses this method
	 *
	 * @throws IncorrectInputException
	 *             if the specified request does not exist
	 * @throws InternalBusinessException
	 *             if a problem occurs while trying to perform the status change
	 */
	private ResponseEntity<Void> changeRequestStatus(final Long inRequestId,
			final RequestStatus inNewStatus)
	{
		try
		{
			if (inNewStatus == RequestStatus.APPROVED)
			{
				requestService.accept(inRequestId);
			} else if (inNewStatus == RequestStatus.REJECTED)
			{
				requestService.reject(inRequestId);
			}
		} catch (final NonExistingResourceException |
				IllegalUpdateOperationException nepe)
		{
			throw new IncorrectInputException(
					MessageFormat.format(NO_REQUEST_FOUND, inRequestId),
					nepe, HttpStatus.NOT_FOUND);

		} catch (final UnsuccessfulUpdateException uue)
		{
			throw new InternalBusinessException(MessageFormat.format(
					REQUEST_ACCEPTANCE_PROBLEM, inRequestId), uue);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
