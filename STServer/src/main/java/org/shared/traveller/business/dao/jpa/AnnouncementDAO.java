package org.shared.traveller.business.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity_;
import org.shared.traveller.business.domain.jpa.CityEntity;
import org.shared.traveller.business.domain.jpa.CityEntity_;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.service.dto.GetAllAnnouncementsRequest;
import org.shared.traveller.rest.param.SortOrder;
import org.springframework.stereotype.Repository;

@Repository
public class AnnouncementDAO extends AbstractDAO<IPersistentAnnouncement>
	implements IAnnouncementDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -3842104507595674655L;

	private static final String NULL_START_CITY =
			"The start city may not be null";

	private static final String NULL_END_CITY =
			"The end city may not be null";

	private static final String NULL_DEP_DATE =
			"The departure date may not be null.";

	private static final String NULL_DRIVER_USRNAME =
			"The driver's username may not be null.";

	@Override
	public List<? extends IPersistentAnnouncement> getAll(
			GetAllAnnouncementsRequest request)
	{
		final TypedQuery<AnnouncementEntity> query = createQuery(request);
		query.setFirstResult(request.getStart());
		query.setMaxResults(request.getCount());
		return query.getResultList();
	}

	@Override
	public long getAllCount(GetAllAnnouncementsRequest request)
	{
		TypedQuery<Long> query = createCountQuery(request);
		return query.getSingleResult();
	}

	@Override
	public AnnouncementEntity loadAnnouncement(
			final String inStartCity,
			final String inEndCity,
			final Date inDepDate,
			final String inDriverUsername) throws DataExtractionException {
		assert null != inStartCity : NULL_START_CITY;
		assert null != inEndCity : NULL_END_CITY;
		assert null != inDepDate : NULL_DEP_DATE;
		assert null != inDriverUsername : NULL_DRIVER_USRNAME;

		DataExtractor<AnnouncementEntity> extractor =
				new DataExtractor<AnnouncementEntity>()
		{
			@Override
			protected void prepareQuery(
					TypedQuery<AnnouncementEntity> inQuery)
			{
				inQuery.setParameter("startPt", inStartCity)
				.setParameter("endPt", inEndCity)
				.setParameter("depDate", inDepDate)
				.setParameter("driver", inDriverUsername).setMaxResults(1);
			}
		};

		final List<AnnouncementEntity> resultList =
				extractor.execute("Announcement.loadAnnouncement",
				entityManager, AnnouncementEntity.class,
				"A problem occurred while trying to extract an announcement.");

		AnnouncementEntity resultEntity = null;
		if(!resultList.isEmpty()) {
			resultEntity = resultList.get(0);
		}

		return resultEntity;
	}

	@Override
	protected Class<? extends IPersistentAnnouncement> getEntityClass()
	{
		return AnnouncementEntity.class;
	}

	private TypedQuery<AnnouncementEntity> createQuery(GetAllAnnouncementsRequest request)
	{
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<AnnouncementEntity> q = cb.createQuery(AnnouncementEntity.class);
		Root<AnnouncementEntity> c = q.from(AnnouncementEntity.class);
		c.fetch(AnnouncementEntity_.startPoint, JoinType.LEFT);
		c.fetch(AnnouncementEntity_.endPoint, JoinType.LEFT);
		c.fetch(AnnouncementEntity_.interPoints, JoinType.LEFT);

		setWhereClause(request, cb, c, q);

		Order order = getOrder(cb, c, request.getSortOrder());
		q.orderBy(order);
		return entityManager.createQuery(q);
	}

	private TypedQuery<Long> createCountQuery(GetAllAnnouncementsRequest request)
	{
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<AnnouncementEntity> c = q.from(AnnouncementEntity.class);
		q.select(cb.count(q.from(AnnouncementEntity.class)));
		setWhereClause(request, cb, c, q);
		Order order = getOrder(cb, c, request.getSortOrder());
		q.orderBy(order);
		return entityManager.createQuery(q);
	}

	private <T> void setWhereClause(GetAllAnnouncementsRequest request, CriteriaBuilder cb, Root<AnnouncementEntity> c,
			CriteriaQuery<T> q)
	{
		if (request.getFrom() != null && request.getFrom().length() > 0)
		{
			Join<AnnouncementEntity, CityEntity> fromJoin = c.join(AnnouncementEntity_.startPoint);
			Predicate p = cb
					.like(cb.lower(fromJoin.get(CityEntity_.name)), "%" + request.getFrom().toLowerCase() + "%");
			q.where(p);
		}
		if (request.getTo() != null && request.getTo().length() > 0)
		{
			Join<AnnouncementEntity, CityEntity> fromJoin = c.join(AnnouncementEntity_.endPoint);
			Predicate p = cb.like(cb.lower(fromJoin.get(CityEntity_.name)), "%" + request.getTo().toLowerCase() + "%");
			q.where(p);
		}
	}

	private Order getOrder(CriteriaBuilder cb, Root<AnnouncementEntity> c, SortOrder order)
	{
		if (order == null)
		{
			order = SortOrder.FROM_ASC;
		}
		switch (order)
		{
		case FROM_ASC:
			return cb.asc(c.get(AnnouncementEntity_.startPoint));
		case FROM_DESC:
			return cb.desc(c.get(AnnouncementEntity_.startPoint));
		case TO_ASC:
			return cb.asc(c.get(AnnouncementEntity_.endPoint));
		case TO_DESC:
			return cb.desc(c.get(AnnouncementEntity_.endPoint));
		default:
			return cb.asc(c.get(AnnouncementEntity_.startPoint));

		}
	}
}
