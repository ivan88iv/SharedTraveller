package org.shared.traveller.business.service.jpa;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.BusinessDomainManager;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.IllegalOperationException;
import org.shared.traveller.business.exception.InfoLookupException;
import org.shared.traveller.business.exception.NonExistingResourceException;
import org.shared.traveller.business.exception.UnsuccessfulResourceCreationException;
import org.shared.traveller.business.exception.UnsuccessfulUpdateException;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.shared.traveller.business.factory.IBusinessDomainFactory;
import org.shared.traveller.business.service.IRequestService;
import org.shared.traveller.business.service.transformation.BusinessDomainTransformerService;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.rest.domain.RequestList;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class represents a business service responsible for the business actions
 * concerning request instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Service
public class RequestService implements IRequestService
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4745891589724917196L;

	private static final String STATUS_CHANGE =
			"the request with id {0} for driver with id {1}.";

	private static final String NON_EXISTING_REQUEST =
			"'request with id {0} for driver with id {1}'";

	private static final String USER_REQUESTS_COUNT_DB_LOOKUP_PROBLEM = "'user with id: {0}'";

	private static final String USER_REQUESTS_DB_LOOKUP_PROBLEM = "'user with id: {0}, startIndes: {1} and count: {2}'";

	private static final String ILLEGAL_REQUEST_ACCEPTANCE =
			"Cannot accept the request with id {0}, because there "
					+ "are no free seats in the vehicle, the announcement is "
					+ "no longer active or the request is not pending.";

	private static final String ILLEGAL_REUQEST_REJECTION =
			"Cannot reject the request with id {0}, because it is not pending "
					+ "or its announcement is not active.";

	private static final String SEARCH_CRITERIA = "announcement's id {0}, "
			+ "driver's id {1}.";

	@Autowired
	private IAnnouncementDAO announcementDAO;

	@Autowired
	private ITravellerDAO travellerDAO;

	@Autowired
	private IRequestDAO requestDAO;

	@Autowired
	private BusinessDomainTransformerService transformationService;

	@Override
	public void createNewRequest(final IPersistentAnnouncement inAnnouncement,
			final IPersistentTraveller inSender)
			throws DataModificationException
	{
		InstanceAsserter.assertNotNull(inAnnouncement, "announcement");
		InstanceAsserter.assertNotNull(inSender, "request sender");

		final IBusinessDomainFactory domainFactory =
				BusinessDomainManager.getInstance().getDomainFactory();
		final IPersistentRequest newRequest = domainFactory.createRequest(
				RequestStatus.PENDING, inSender, inAnnouncement);
		try
		{
			requestDAO.insert(newRequest);
		} catch (final DataModificationException dme)
		{
			throw new UnsuccessfulResourceCreationException("the request "
					+ newRequest.toString(), dme);
		}
	}

	@Transactional
	@Override
	public IRequestedAnnouncement reject(final Long inRequestId,
			final Long inDriverId)
	{
		InstanceAsserter.assertNotNull(inRequestId, "request's id");
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");

		IPersistentRequest persistentRequest =
				findRequestToUpdate(inRequestId, inDriverId);

		if (persistentRequest.getStatus() == RequestStatus.PENDING &&
				persistentRequest.getAnnouncement().getStatus() ==
				Status.ACTIVE)
		{
			persistentRequest.setStatus(RequestStatus.REJECTED);
			persistentRequest = requestDAO.update(persistentRequest);
			return transformationService
					.getRequestedAnnouncement(persistentRequest
							.getAnnouncement());
		} else
		{
			throw new IllegalOperationException(MessageFormat.format(
					ILLEGAL_REUQEST_REJECTION, inRequestId));
		}
	}

	@Transactional
	@Override
	public IRequestedAnnouncement accept(final Long inRequestId,
			final Long inDriverId)
	{
		InstanceAsserter.assertNotNull(inRequestId, "request's id");
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");

		IPersistentRequest persistentRequest =
				findRequestToUpdate(inRequestId, inDriverId);
		IPersistentAnnouncement requestedAnnouncement =
				persistentRequest.getAnnouncement();

		if (persistentRequest.getStatus() == RequestStatus.PENDING &&
				requestedAnnouncement.getStatus() == Status.ACTIVE &&
				requestedAnnouncement.getFreeSeats() > 0)
		{
			persistentRequest.setStatus(RequestStatus.APPROVED);
			final short newFreeSeats = (short) (persistentRequest
					.getAnnouncement().getFreeSeats() - 1);
			persistentRequest.getAnnouncement().setFreeSeats(
					Short.valueOf(newFreeSeats));
			persistentRequest = requestDAO.update(persistentRequest);
			return transformationService
					.getRequestedAnnouncement(requestedAnnouncement);
		} else
		{
			throw new IllegalOperationException(MessageFormat.format(
					ILLEGAL_REQUEST_ACCEPTANCE, inRequestId));
		}
	}

	@Override
	public RequestList getUserRequests(final AuthenticatedUser inUser,
			final int inStartIndex, final int inCount)
	{
		InstanceAsserter.assertNotNull(inUser, "driver");

		RequestList result = new RequestList();

		Long userRequestsCount = getUserRequestsCount(inUser);
		if (userRequestsCount > 0
				&& inStartIndex < userRequestsCount.intValue())
		{
			List<? extends IPersistentRequest> userRequestsFromDb = getUserRequestsFromDb(
					inUser, inStartIndex, inCount);

			final List<IRequestInfo> userRequests = new ArrayList<>();
			for (final IPersistentRequest request : userRequestsFromDb)
			{
				userRequests.add(transformationService
						.getRequestInformation(request));
			}
			result.setCount(userRequestsCount.intValue());
			result.setList(userRequests);
		}

		return result;
	}

	@Override
	public Long getUserRequestsCount(final AuthenticatedUser inUser)
	{
		Long userRequestscount = 0l;
		try
		{
			userRequestscount = requestDAO.findUserRequestsCount(inUser);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("requests", MessageFormat.format(
					USER_REQUESTS_COUNT_DB_LOOKUP_PROBLEM,
					inUser.getId()));
		}
		return userRequestscount;
	}

	@Override
	public IRequestedAnnouncement loadRequestInfo(
			final Long inAnnouncementId, final Long inDriverId)
	{
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement's id");
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");

		IPersistentAnnouncement requestedAnnouncement = null;

		try
		{
			requestedAnnouncement = announcementDAO
					.findAnnouncementWithRequests(
							inAnnouncementId, inDriverId);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("request information",
					MessageFormat.format(SEARCH_CRITERIA, inAnnouncementId,
							inDriverId));
		}

		return transformationService
				.getRequestedAnnouncement(requestedAnnouncement);
	}

	/**
	 * The method returns the request for the specified id and driver
	 * 
	 * @param inRequestId
	 *            the id of the request to be found. It may not be null
	 * @param inDriverId
	 *            the id of the driver for who request is searched. It may not
	 *            be null
	 * @return the found request
	 * @throws UnsuccessfulUpdateException
	 *             if a problem occurs while trying to find the request which is
	 *             later to be updated
	 * @throws NonExistingResourceException
	 *             if no such request is found
	 */
	private IPersistentRequest findRequestToUpdate(final Long inRequestId,
			final Long inDriverId)
	{
		IPersistentRequest persistentRequest = null;

		try
		{
			persistentRequest = requestDAO.findRequest(inDriverId, inRequestId);
		} catch (final DataExtractionException dee)
		{
			throw new UnsuccessfulUpdateException(MessageFormat.format(
					STATUS_CHANGE, inRequestId, inDriverId), dee);
		}

		if (null == persistentRequest)
		{
			throw new NonExistingResourceException(MessageFormat.format(
					NON_EXISTING_REQUEST, inRequestId, inDriverId));
		}

		return persistentRequest;
	}

	private List<? extends IPersistentRequest> getUserRequestsFromDb(
			final AuthenticatedUser inUser,
			final int inStartIndex, final int inCount)
	{
		List<? extends IPersistentRequest> userRequests = null;
		try
		{
			userRequests = requestDAO.findUserRequests(inUser, inStartIndex,
					inCount);
		} catch (final DataExtractionException dee)
		{
			throw new InfoLookupException("requests", MessageFormat.format(
					USER_REQUESTS_DB_LOOKUP_PROBLEM,
					inUser.getId(), inStartIndex, inCount));
		}
		return userRequests;
	}

}
