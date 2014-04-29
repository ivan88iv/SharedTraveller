package org.shared.traveller.business.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.InstanceAsserter;

@Entity(name = "Request")
@Table(name = "request")
@NamedQueries(
{
		@NamedQuery(name = "Request.loadRequests", query = "SELECT r FROM Request r "
				+ "INNER JOIN r.announcement ann "
				+ "WHERE ann.startPoint.name = :startPt AND "
				+ "ann.endPoint.name = :endPt AND "
				+ "ann.departureDate = :depDate AND "
				+ "ann.driver.username = :driver"),
		@NamedQuery(name = RequestNamedQueryNames.LOAD_ANNOUNCEMENT_REQUESTS,
				query = "SELECT r FROM Request r INNER JOIN r.announcement ann "
						+ "WHERE ann.id = :announcementId"),
		@NamedQuery(name = "Request.find", query = "SELECT r FROM Request r "
				+ "INNER JOIN r.announcement ann "
				+ "WHERE ann.driver.username = :driver AND " + "r.id = :id"),
		@NamedQuery(name = RequestNamedQueryNames.GET_REQUESTS_FOR_USER, query = "SELECT r from Request r inner join fetch r.announcement "
				+ " inner join fetch r.traveller  where r.traveller = :traveller"),
		@NamedQuery(name = RequestNamedQueryNames.GET_REQUESTS_COUNT_FOR_USER, query = "SELECT count(r) from Request r  where r.traveller = :traveller") })
public class RequestEntity extends AbstractEntity implements IPersistentRequest
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -6146193376219161154L;

	@Id
	@GeneratedValue
	@Column(name = "ID", updatable = false)
	private Long id;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@ManyToOne
	@JoinColumn(name = "TRAVELLER_ID")
	private TravellerEntity traveller;

	@ManyToOne
	@JoinColumn(name = "ANNOUNCEMENT_ID")
	private AnnouncementEntity announcement;

	protected RequestEntity()
	{
		// used beacause of JPA
	}

	/**
	 * The constructor creates a new JPA request entity
	 * 
	 * @param inStatus
	 *            the status of the request. It may not be null.
	 * @param inTraveller
	 *            the traveller associated with the request. It may not be null.
	 * @param inAnnouncemnet
	 *            the announcement associated with the request. It may not be
	 *            null.
	 */
	public RequestEntity(final RequestStatus inStatus,
			final TravellerEntity inTraveller,
			final AnnouncementEntity inAnnouncemnet)
	{
		InstanceAsserter.assertNotNull(inStatus, "status");
		InstanceAsserter.assertNotNull(inTraveller, "traveller");
		InstanceAsserter.assertNotNull(inAnnouncemnet, "announcement");

		status = inStatus;
		traveller = inTraveller;
		announcement = inAnnouncemnet;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public IPersistentTraveller getSender()
	{
		// TODO deep copy
		return traveller;
	}

	@Override
	public IPersistentAnnouncement getAnnouncement()
	{
		// TODO deep copy
		return announcement;
	}

	@Override
	public RequestStatus getStatus()
	{
		return status;
	}

	@Override
	public void setStatus(RequestStatus inStatus)
	{
		status = inStatus;
	}
}
