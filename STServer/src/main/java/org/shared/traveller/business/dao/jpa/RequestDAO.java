package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.jpa.RequestEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.springframework.stereotype.Repository;

/**
 * The class is used for data access operations that manage request entities.
 * These operations are performed using JPA.
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Repository
public class RequestDAO extends AbstractDAO<IPersistentRequest> implements
		IRequestDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 4708301765173838117L;

	private static final String NULL_START_POINT = "The start point may not be null";

	private static final String NULL_END_POINT = "The end point may not be null";

	private static final String NULL_DEPARTURE_DATE = "The departure date may not be null";

	private static final String NULL_DRIVER_NAME = "The driver's user name may not be null";

	private static final String NULL_DRIVER = "The driver's user name may not be null.";

	private static final String NULL_REQUEST_ID = "The request id may not be null.";

	private static final String REQUEST_EXTRACTION_PROBLEM = "A problem occurred while trying to extract "
			+ "the request information for the announcement data: "
			+ "start point: {0}\nend point: {1}\ndeparture date: {2}\n"
			+ "driver user name: {3}";

	private static final String LOAD_REQUEST_PROBLEM =
			"A problem occurred while trying to load requests for announcement with "
					+ "id {0}";

	private static final String FIND_REQUEST_BY_ID_ERROR = "A problem occurred while searching for request with id {0} "
			+ "for driver {1}.";

	private static final String FIND_USER_REQUESTS_ERROR = "A problem occurred while searching user : {0} reuqests.";

	@Override
	public List<RequestEntity> loadRequests(final String inStartPt,
			final String inEndPt, final Date inDepDate,
			final String inDriverUsrname)
	{
		assert null != inStartPt : NULL_START_POINT;
		assert null != inEndPt : NULL_END_POINT;
		assert null != inDepDate : NULL_DEPARTURE_DATE;
		assert null != inDriverUsrname : NULL_DRIVER_NAME;

		DataExtractor<RequestEntity> extractor = new DataExtractor<RequestEntity>()
		{
			@Override
			protected void prepareQuery(TypedQuery<RequestEntity> inQuery)
			{
				inQuery.setParameter("startPt", inStartPt)
						.setParameter("endPt", inEndPt)
						.setParameter("depDate", inDepDate)
						.setParameter("driver", inDriverUsrname);
			}
		};

		return extractor.execute("Request.loadRequests", entityManager,
				RequestEntity.class,
				MessageFormat.format(REQUEST_EXTRACTION_PROBLEM, inStartPt,
						inEndPt, inDepDate, inDriverUsrname));
	}

	@Override
	public IPersistentRequest findRequest(final String inDriver,
			final Long inRequestId)
	{
		assert null != inDriver : NULL_DRIVER;
		assert null != inRequestId : NULL_REQUEST_ID;

		final DataExtractor<RequestEntity> extractor = new DataExtractor<RequestEntity>()
		{
			@Override
			protected void prepareQuery(final TypedQuery<RequestEntity> inQuery)
			{
				inQuery.setParameter("driver", inDriver).setParameter("id",
						inRequestId);
			}
		};

		final List<RequestEntity> results = extractor.execute("Request.find",
				entityManager, RequestEntity.class,
				MessageFormat.format(FIND_REQUEST_BY_ID_ERROR, inRequestId,
						inDriver));
		RequestEntity request = null;

		if (!results.isEmpty())
		{
			request = results.get(0);
		}

		return request;
	}

	@Override
	public Long findUserRequestsCount(final AuthenticatedUser inUser)
	{
		TypedQuery<Long> countQuery = entityManager.createNamedQuery(
				RequestNamedQueryNames.GET_REQUESTS_COUNT_FOR_USER, Long.class);
		TravellerEntity traveller = new TravellerEntity();
		traveller.setId(inUser.getId());
		countQuery.setParameter("traveller", traveller);

		return countQuery.getSingleResult();
	}

	@Override
	public List<RequestEntity> findUserRequests(final AuthenticatedUser inUser,
			final int inStartIndex,
			final int inMaxResult)
	{

		final DataExtractor<RequestEntity> extractor = new DataExtractor<RequestEntity>()
		{

			@Override
			protected void prepareQuery(TypedQuery<RequestEntity> inQuery)
			{
				TravellerEntity traveller = new TravellerEntity();
				traveller.setId(inUser.getId());
				inQuery.setParameter("traveller", traveller);
				inQuery.setFirstResult(inStartIndex);
				inQuery.setMaxResults(inMaxResult);
			}
		};

		return extractor.execute(
				RequestNamedQueryNames.GET_REQUESTS_FOR_USER,
				entityManager,
				RequestEntity.class,
				MessageFormat.format(FIND_USER_REQUESTS_ERROR,
						inUser.getUsername()));
	}

	@Override
	public List<? extends IPersistentRequest> loadRequests(
			final Long inAnnouncementId)
	{
		final DataExtractor<RequestEntity> extractor =
				new DataExtractor<RequestEntity>()
				{
					@Override
					protected void prepareQuery(
							TypedQuery<RequestEntity> inQuery)
					{
						inQuery.setParameter("announcementId", inAnnouncementId);
					}
				};
		return extractor.execute(
				RequestNamedQueryNames.LOAD_ANNOUNCEMENT_REQUESTS,
				entityManager, RequestEntity.class,
				MessageFormat.format(LOAD_REQUEST_PROBLEM, inAnnouncementId));
	}

	@Override
	protected Class<? extends RequestEntity> getEntityClass()
	{
		return RequestEntity.class;
	}
}
