package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.authentication.domain.AuthenticatedUser;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.jpa.RequestEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.utility.InstanceAsserter;
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

	private static final String LOAD_REQUEST_PROBLEM =
			"A problem occurred while trying to load requests for "
					+ "announcement with id {0} and driver with id {1}.";

	private static final String FIND_REQUEST_BY_ID_ERROR =
			"A problem occurred while searching for request with id {0} "
					+ "for driver with id {1}.";

	private static final String FIND_USER_REQUESTS_ERROR = "A problem occurred while searching user : {0} reuqests.";

	@Override
	public IPersistentRequest findRequest(final Long inDriverId,
			final Long inRequestId)
	{
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");
		InstanceAsserter.assertNotNull(inRequestId, "request's id");

		final DataExtractor<RequestEntity> extractor = new DataExtractor<RequestEntity>()
		{
			@Override
			protected void prepareQuery(final TypedQuery<RequestEntity> inQuery)
			{
				inQuery.setParameter("driverId", inDriverId)
						.setParameter("id", inRequestId);
			}
		};

		final List<RequestEntity> results = extractor.execute(
				RequestNamedQueryNames.FIND_REQUEST, entityManager,
				RequestEntity.class,
				MessageFormat.format(FIND_REQUEST_BY_ID_ERROR, inRequestId,
						inDriverId));
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
			final Long inAnnouncementId, final Long inDriverId)
	{
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement's id");
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");

		final DataExtractor<RequestEntity> extractor =
				new DataExtractor<RequestEntity>()
				{
					@Override
					protected void prepareQuery(
							TypedQuery<RequestEntity> inQuery)
					{
						inQuery.setParameter("announcementId", inAnnouncementId)
								.setParameter("driverId", inDriverId);
					}
				};
		return extractor.execute(
				RequestNamedQueryNames.LOAD_ANNOUNCEMENT_REQUESTS,
				entityManager, RequestEntity.class,
				MessageFormat.format(LOAD_REQUEST_PROBLEM, inAnnouncementId,
						inDriverId));
	}

	@Override
	protected Class<? extends RequestEntity> getEntityClass()
	{
		return RequestEntity.class;
	}
}
