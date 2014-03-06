package org.shared.traveller.client.service.rest;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.InternalBusinessException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.business.service.RequestService;
import org.shared.traveller.business.service.TravellerService;
import org.shared.traveller.client.domain.rest.event.NewRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/request")
public class RestRequestService
{
	private static final String NULL_NEW_REQUEST_EVENT =
			"The event for creating a new request may not be null.";

	private static final String TRAVEL_REQUEST_SEND_PROBLEM =
			"Could not send a new travel request";

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private TravellerService travellerService;

	@Autowired
	private RequestService requestService;

	/**
	 * The method is a rest service method that is responsible
	 * for the creation of a new request business object. It
	 * uses a special event object for this purpose.
	 *
	 * @param inEvent the {@link NewRequestEvent} instance
	 * used for the particular request creation
	 * @return {@link HttpStatus#NOT_FOUND} status if the announcement
	 * or the traveller specified by the event do not exist and
	 * {@link HttpStatus#CREATED} if the request instance was successfully
	 * created.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.PUT)
	public ResponseEntity<Void> sendTravelRequest(
			@RequestBody final NewRequestEvent inEvent) {
		assert null != inEvent : NULL_NEW_REQUEST_EVENT;

		IPersistentAnnouncement loadedAnnouncement = null;
		IPersistentTraveller sender = null;
		try {
			loadedAnnouncement = announcementService.loadAnnouncement(
					inEvent.getFromPoint(),
					inEvent.getToPoint(),
					inEvent.getDepartureDate(),
					inEvent.getDriver());
			sender = travellerService.findByUsername(inEvent.getSender());
		} catch(final DataExtractionException dee) {
			throw new InternalBusinessException(
					TRAVEL_REQUEST_SEND_PROBLEM, dee);
		}

		final Void result = null;
		ResponseEntity<Void> response =
				new ResponseEntity<Void>(result, HttpStatus.NOT_FOUND);
		if(null != loadedAnnouncement && null != sender) {
			try {
				requestService.createNewRequest(loadedAnnouncement, sender);
			} catch(final DataModificationException dme) {
				throw new InternalBusinessException(
						TRAVEL_REQUEST_SEND_PROBLEM, dme);
			}

			response = new ResponseEntity<Void>(result, HttpStatus.CREATED);
		}

		return response;
	}
}
