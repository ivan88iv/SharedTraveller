package org.shared.traveller.client.service.rest;

import java.text.MessageFormat;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IllegalOperationException;
import org.shared.traveller.business.exception.IncorrectDomainTypeException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.exception.UnsuccessfulResourceDeletionException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.business.service.IRequestService;
import org.shared.traveller.business.service.NotificationService;
import org.shared.traveller.business.service.TravellerService;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.request.rest.RequestInfo;
import org.shared.traveller.client.domain.rest.RequestedAnnouncement;
import org.shared.traveller.client.exception.rest.IllegalOperationRequested;
import org.shared.traveller.client.exception.rest.IncorrectInputException;
import org.shared.traveller.client.exception.rest.InternalBusinessException;
import org.shared.traveller.rest.domain.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	private static final String NO_REQUEST_FOUND =
			"No request exist that can be updated.";

	private static final String REQUEST_STATUS_UPDATE_PROBLEM =
			"A problem occurred while trying to update the status of the request";

	private static final String NULL_ANNOUNCEMENT_ID =
			"The announcement id may not be null.";

	private static final String REQUEST_INFO_EXTRACTION_PROBLEM =
			"A problem occurred while trying to extract request announcement "
					+ "information for announcement's id {0} and "
					+ "driver's id {1}.";

	private static final String WRONG_REQUEST_INDEX =
			"No request with index {0} exists in the request information {1}.";

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private TravellerService travellerService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private NotificationService notificationService;

	/**
	 * * The method is a rest service method that is responsible for the
	 * creation of a new request business object. It uses a special event object
	 * for this purpose.
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
					inRequest.getDepartureDate(),
					inRequest.getDriver().getUsername());
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
				UnsuccessfulResourceCreationException urce)
		{
			throw new InternalBusinessException(MessageFormat.format(
					TRAVEL_REQUEST_SEND_PROBLEM, inRequest), urce);
		}

		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}

	/**
	 * The method retrieves the all requests and some basic announcement
	 * information for the announcement instance with the specified id
	 * 
	 * @param inAnnouncementId
	 *            the id of the announcement for which request information is
	 *            retrieved
	 * @return the request information associated with the specified
	 *         announcement with {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             with {@link HttpStatus#NOT_FOUND} if the id of the
	 *             announcement is null
	 * @throws InternalBusinessException
	 *             if a problem occurs in the extraction process
	 */
	@RequestMapping(value = "/info/for/announcement/{announcementId}",
			method = RequestMethod.POST)
	public ResponseEntity<IRequestedAnnouncement> getAnnouncementRequestInfo(
			@PathVariable(value = "announcementId") final Long inAnnouncementId)
	{
		final AuthenticatedUser authUsr = new AuthenticatedUser(1l, "temp");

		if (null == inAnnouncementId)
		{
			throw new IncorrectInputException(NULL_ANNOUNCEMENT_ID,
					HttpStatus.NOT_FOUND);
		}

		ResponseEntity<IRequestedAnnouncement> response = null;

		try
		{
			response = new ResponseEntity<IRequestedAnnouncement>(
					requestService.loadRequestInfo(
							inAnnouncementId, authUsr.getId()),
					HttpStatus.OK);
			notificationService.removeDriverNotifications(authUsr.getId(),
					inAnnouncementId);
		} catch (final UnsuccessfulResourceDeletionException |
				InfoLookupException ile)
		{
			throw new InternalBusinessException(
					MessageFormat.format(REQUEST_INFO_EXTRACTION_PROBLEM,
							inAnnouncementId, authUsr.getId()), ile);
		}

		return response;
	}

	/**
	 * The method accepts the provided request
	 * 
	 * @param inRequestInd
	 *            the index of the request to be accepted
	 * @param inRequestInfo
	 *            the request information holding the request to be accepted
	 * 
	 * @return the newly updated request information with status code
	 *         {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             if the request information is null, the request index is
	 *             invalid or there is no such request in the database. The
	 *             response status is {@link HttpStatus.NOT_FOUND}
	 * 
	 * @throws IllegalOperationRequested
	 *             if the announcement for the request is not active, if the
	 *             request is not pending or if there are not free seats for the
	 *             announcement.
	 * 
	 * @throws InternalBusinessException
	 *             if a problem occurs in the process of updating the status
	 */
	@RequestMapping(value = "/info/update/accept/{requestInd}",
			method = RequestMethod.POST)
	public ResponseEntity<IRequestedAnnouncement> acceptRequest(
			@PathVariable(value = "requestInd") final int inRequestInd,
			@RequestBody final RequestedAnnouncement inRequestInfo)
	{
		return changeRequestStatus(inRequestInfo, inRequestInd,
				RequestStatus.APPROVED);
	}

	/**
	 * The method rejects the provided request
	 * 
	 * @param inRequestInd
	 *            the index of the request to be rejected
	 * @param inRequestInfo
	 *            the request information that holds the request to be updated
	 * 
	 * @return the newly updated request information with status code
	 *         {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             if the request information is null, the request index is
	 *             invalid or there is no such request in the database. The
	 *             response status is {@link HttpStatus.NOT_FOUND}
	 * 
	 * @throws IllegalOperationRequested
	 *             if the announcement for the request is not active or if the
	 *             request is not pending
	 * 
	 * @throws InternalBusinessException
	 *             if a problem occurs in the process of updating the status
	 */
	@RequestMapping(value = "/info/update/reject/{requestInd}",
			method = RequestMethod.POST)
	public ResponseEntity<IRequestedAnnouncement> rejectRequest(
			@PathVariable(value = "requestInd") final int inRequestInd,
			@RequestBody final RequestedAnnouncement inRequestInfo)
	{
		return changeRequestStatus(inRequestInfo, inRequestInd,
				RequestStatus.REJECTED);
	}

	/**
	 * The method declines the provided request
	 * 
	 * @param inRequestId
	 *            the id of the request to be declined
	 * 
	 * @return an empty result
	 * @throws IncorrectInputException
	 *             if the specified request does not exist
	 * @throws InternalBusinessException
	 *             if a problem occurs while trying to perform the status change
	 */
	@RequestMapping(value = "/decline", method = RequestMethod.POST)
	public ResponseEntity<Void> declineRequest(
			@RequestParam(value = "id") final Long inRequestId)
	{
		try
		{
			// TODO call a suitable request service method that declines
			// the current request
			notificationService.createNewRequestNotification(
					inRequestId, Type.REQUEST_DECLINATION);
		} catch (final NonExistingResourceException |
				IllegalOperationException nepe)
		{
			throw new IncorrectInputException(MessageFormat.format(
					NO_REQUEST_FOUND, inRequestId), nepe,
					HttpStatus.NOT_FOUND);

		} catch (final InfoLookupException |
				UnsuccessfulResourceCreationException |
				UnsuccessfulUpdateException uue)
		{
			throw new InternalBusinessException(MessageFormat.format(
					REQUEST_STATUS_UPDATE_PROBLEM, inRequestId), uue);
		}
		return new ResponseEntity<>(HttpStatus.OK);
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
	 * The method changes the status of the request with the specified index
	 * inside the request information
	 * 
	 * @param inRequestInfo
	 *            the request information holding the request to be updated
	 * @param inRequestInd
	 *            the index of the request to be updated
	 * @param inNewStatus
	 *            the new status of the request
	 * @return the updated request information after the update. The response
	 *         status is {@link HttpStatus#OK}
	 * 
	 * @throws IncorrectInputException
	 *             if the request information is null, the request index is
	 *             invalid or there is no such request in the database. The
	 *             response status is {@link HttpStatus#NOT_FOUND}
	 * @throws IllegalOperationRequested
	 *             if the announcement for the request is not active, if the
	 *             request is not pending or if the new status is
	 *             {@link RequestStatus#APPROVED} and there are not free seats
	 *             for the announcement.
	 * @throws InternalBusinessException
	 *             if a problem occurs in the process of updating the status
	 */
	private ResponseEntity<IRequestedAnnouncement> changeRequestStatus(
			final IRequestedAnnouncement inRequestInfo,
			final int inRequestInd, final RequestStatus inNewStatus)
	{
		if (null == inRequestInfo || inRequestInd < 0
				|| inRequestInd > inRequestInfo.getRequests().size())
		{
			throw new IncorrectInputException(MessageFormat.format(
					WRONG_REQUEST_INDEX, inRequestInd, inRequestInfo),
					HttpStatus.NOT_FOUND);
		}

		final Long requestId = inRequestInfo.getRequests()
				.get(inRequestInd).getId();
		// TODO auth info
		final AuthenticatedUser authUser = new AuthenticatedUser(1l, "temp");
		IRequestedAnnouncement requestInfo = null;

		try
		{
			if (inNewStatus == RequestStatus.APPROVED)
			{
				requestInfo = requestService
						.accept(requestId, authUser.getId());
				notificationService.createNewRequestNotification(
						requestId, Type.REQUEST_ACCEPTANCE);
			} else if (inNewStatus == RequestStatus.REJECTED)
			{
				requestInfo = requestService
						.reject(requestId, authUser.getId());
				notificationService.createNewRequestNotification(
						requestId, Type.REQUEST_REJECTION);
			}
		} catch (final NonExistingResourceException nepe)
		{
			throw new IncorrectInputException(NO_REQUEST_FOUND, nepe,
					HttpStatus.NOT_FOUND);
		} catch (IllegalOperationException iupe)
		{
			throw new IllegalOperationRequested("request status update", iupe);
		} catch (final InfoLookupException |
				UnsuccessfulResourceCreationException |
				UnsuccessfulUpdateException uue)
		{
			throw new InternalBusinessException(REQUEST_STATUS_UPDATE_PROBLEM,
					uue);
		}

		return new ResponseEntity<IRequestedAnnouncement>(requestInfo,
				HttpStatus.OK);
	}
}
