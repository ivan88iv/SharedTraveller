package org.shared.traveller.business.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.rest.RequestInfo;
import org.shared.traveller.client.domain.rest.RequestInfo.RequestInfoBuilder;
import org.shared.traveller.rest.domain.RequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents a business service responsible for the business actions
 * concerning request instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Service
public class RequestService implements Serializable
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4745891589724917196L;

	private static final String NULL_REQUEST_ID = "The request id may not be null.";

	private static final String NULL_START_PT = "The start point may not be null.";

	private static final String NULL_END_PT = "The end point may not be null.";

	private static final String NULL_DEPARTURE_DATE = "The departure date may not be null.";

	private static final String NULL_DRIVER_USERNAME = "The user name of the driver may not be null.";

	private static final String NULL_USER = "The user name of the driver may not be null.";

	private static final String NULL_ANNOUNCEMENT = "The announcement may not be null.";

	private static final String NULL_SENDER = "The user name of the request sender may not be null.";

	private static final String SEARCH_CRITERIA = "start point: {0}, end point: {1}, departure date: {2} and "
			+ "driver's username: {3}";

	private static final String STATUS_CHANGE = "the request with id {0} for the user {1}.";

	private static final String NON_EXISTING_REQUEST = "'request with id {0} for the user {1}'";

	private static final String USER_REQUESTS_COUNT_DB_LOOKUP_PROBLEM = "'user with id: {0}'";

	private static final String USER_REQUESTS_DB_LOOKUP_PROBLEM = "'user with id: {0}, startIndes: {1} and count: {2}'";

	@Autowired
	private IAnnouncementDAO announcementDAO;

	@Autowired
	private ITravellerDAO travellerDAO;

	@Autowired
	private IRequestDAO requestDAO;

	/**
	 * The methods creates a new request, sent by a specific user for a specific
	 * announcement.
	 * 
	 * @param inAnnouncement
	 *            the announcement for which a new request is created.It may not
	 *            be null.
	 * @param inSender
	 *            the user that sent the request. It may not be null.
	 * 
	 * @throws DataModificationException
	 *             if an error occurs while trying to save the new request
	 */
	public void createNewRequest(final IPersistentAnnouncement inAnnouncement, final IPersistentTraveller inSender)
			throws DataModificationException
	{
		assert null != inAnnouncement : NULL_ANNOUNCEMENT;
		assert null != inSender : NULL_SENDER;

		requestDAO.saveNewRequest(inSender, inAnnouncement);
	}

	/**
	 * The method loads and returns the request information for the specified
	 * announcement information
	 * 
	 * @param inStartPt
	 *            the start point in the announcement. It may not be null.
	 * @param inEndPt
	 *            the end point in the announcement. It may not be null.
	 * @param inDepDate
	 *            the departure date in the announcement. It may not be null.
	 * @param inDriverUsrname
	 *            the user name of the driver specified in the announcement. It
	 *            may not be null.
	 * @return a list of the request information instances that are related to
	 *         the the specified announcement information
	 * 
	 * @throws InfoLookupException
	 *             if the requests cannot be loaded due to a problem in the
	 *             search process
	 */
	public List<? extends IRequestInfo> loadRequests(final String inStartPt, final String inEndPt,
			final Date inDepDate, final String inDriverUsrname)
	{
		assert null != inStartPt : NULL_START_PT;
		assert null != inEndPt : NULL_END_PT;
		assert null != inDepDate : NULL_DEPARTURE_DATE;
		assert null != inDriverUsrname : NULL_DRIVER_USERNAME;

		IPersistentAnnouncement announcement = null;
		try
		{
			announcement = announcementDAO.loadAnnouncement(inStartPt, inEndPt, inDepDate, inDriverUsrname);

		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("requests", MessageFormat.format(SEARCH_CRITERIA, inStartPt, inEndPt,
					inDepDate, inDriverUsrname));
		}

		final List<RequestInfo> requestInfo = new ArrayList<>();

		if (null != announcement)
		{
			final Status announcementStatus = announcement.getStatus();
			List<? extends IPersistentRequest> persistentRequests = null;

			try
			{
				persistentRequests = requestDAO.loadRequests(inStartPt, inEndPt, inDepDate, inDriverUsrname);
			} catch (final DataExtractionException dee)
			{
				throw new InfoLookupException("requests", MessageFormat.format(SEARCH_CRITERIA, inStartPt, inEndPt,
						inDepDate, inDriverUsrname));
			}

			for (final IPersistentRequest request : persistentRequests)
			{
				final RequestInfoBuilder builder = new RequestInfoBuilder();
				builder.id(request.getId()).fromPoint(inStartPt).toPoint(inEndPt).departureDate(inDepDate)
						.driverUsername(inDriverUsrname).sender(request.getSender().getUsername())
						.status(request.getStatus()).announcementStatus(announcementStatus);
				requestInfo.add(builder.build());
			}
		}

		return requestInfo;
	}

	/**
	 * The method changes the status of the request with the specified
	 * identification
	 * 
	 * @param inRequestId
	 *            the id of the request which status is to be changed
	 * @param inNewStatus
	 *            the new status to change the request's status to
	 */
	public void changeStatus(final Long inRequestId, final RequestStatus inNewStatus)
	{
		assert null != inRequestId : NULL_REQUEST_ID;

		IPersistentRequest persistentRequest = null;

		try
		{
			// TODO replace the hard-coded driver with real one
			persistentRequest = requestDAO.findRequest("temp", inRequestId);
		} catch (final DataExtractionException dee)
		{
			throw new UnsuccessfulUpdateException(MessageFormat.format(STATUS_CHANGE, inRequestId, "temp"), dee);
		}

		if (null == persistentRequest)
		{
			throw new NonExistingResourceException(MessageFormat.format(NON_EXISTING_REQUEST, inRequestId, "temp"));
		}

		persistentRequest.setStatus(inNewStatus);
		requestDAO.merge(persistentRequest);
	}

	public RequestList getUserRequests(final AuthenticatedUser inUser, final int inStartIndex, final int inCount)
	{
		assert null != inUser : NULL_USER;

		RequestList result = new RequestList();

		Long userRequestsCount = getUserRequestsCount(inUser);
		if (userRequestsCount > 0 && inStartIndex < userRequestsCount.intValue())
		{

			List<? extends IPersistentRequest> userRequestsFromDb = getUserRequestsFromDb(inUser, inStartIndex, inCount);

			final List<IRequestInfo> userRequests = new ArrayList<>();

			for (final IPersistentRequest request : userRequestsFromDb)
			{
				final RequestInfoBuilder builder = new RequestInfoBuilder();
				final IPersistentAnnouncement anno = request.getAnnouncement();
				builder.id(request.getId()).fromPoint(anno.getStartPoint().getName())
						.toPoint(anno.getEndPoint().getName()).departureDate(anno.getDepartureDate())
						.driverUsername(request.getAnnouncement().getDriver().getUsername())
						.sender(request.getSender().getUsername()).status(request.getStatus())
						.announcementStatus(anno.getStatus());
				userRequests.add(builder.build());
			}
			result.setCount(userRequestsCount.intValue());
			result.setList(userRequests);
		}

		return result;
	}

	public Long getUserRequestsCount(final AuthenticatedUser inUser)
	{
		Long userRequestscount = 0l;
		try
		{
			userRequestscount = requestDAO.findUserRequestsCount(inUser);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("requests", MessageFormat.format(USER_REQUESTS_COUNT_DB_LOOKUP_PROBLEM,
					inUser.getId()));
		}
		return userRequestscount;
	}

	private List<? extends IPersistentRequest> getUserRequestsFromDb(final AuthenticatedUser inUser,
			final int inStartIndex, final int inCount)
	{
		List<? extends IPersistentRequest> userRequests = null;
		try
		{
			userRequests = requestDAO.findUserRequests(inUser, inStartIndex, inCount);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("requests", MessageFormat.format(USER_REQUESTS_DB_LOOKUP_PROBLEM,
					inUser.getId(), inStartIndex, inCount));
		}
		return userRequests;
	}
}
