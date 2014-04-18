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
import org.shared.traveller.client.domain.request.RequestStatus;

@Entity(name = "Request")
@Table(name = "request")
@NamedQueries({
	@NamedQuery(name = "Request.loadRequests",
			query = "SELECT r FROM Request r "
				+ "INNER JOIN r.announcement ann "
				+ "WHERE ann.startPoint.name = :startPt AND "
				+ "ann.endPoint.name = :endPt AND "
				+ "ann.departureDate = :depDate AND "
				+ "ann.driver.username = :driver"),
	@NamedQuery(name = "Request.find",
			query = "SELECT r FROM Request r "
					+ "INNER JOIN r.announcement ann "
					+ "WHERE ann.driver.username = :driver AND "
					+ "r.id = :id")
})
public class RequestEntity extends AbstractEntity implements IPersistentRequest
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -6146193376219161154L;

	@Id
	@GeneratedValue
	@Column(name = "ID", updatable=false)
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

	protected RequestEntity() {
		// used beacause of JPA
	}

	public RequestEntity(final RequestStatus inStatus,
			final TravellerEntity inTraveller,
			final AnnouncementEntity inAnnouncemnet) {
		status = inStatus;
		traveller = inTraveller;
		announcement = inAnnouncemnet;
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

	@Override
	public Long getId()
	{
		return id;
	}
}
