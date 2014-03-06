package org.shared.traveller.business.dao.jpa;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentRequest;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.enumeration.RequestStatus;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity;
import org.shared.traveller.business.domain.jpa.RequestEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class is used for data access operations that manage
 * request entities. These operations are performed using
 * JPA.
 *
 * @author "Ivan Ivanov"
 *
 */
@Repository
public class RequestDAO extends AbstractDAO<IPersistentRequest>
	implements IRequestDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 4708301765173838117L;

	private static final String NULL_SENDER =
			"The sender of the request may not be null.";

	private static final String NULL_ANNOUNCEMENT =
			"The announcement for which a request is sent " +
			"may not be null";

	@Transactional
	@Override
	public void saveNewRequest(final IPersistentTraveller inSender,
			final IPersistentAnnouncement inAnnouncement)
	throws DataModificationException {
		assert null != inSender : NULL_SENDER;
		assert null != inAnnouncement : NULL_ANNOUNCEMENT;

		if(inSender instanceof TravellerEntity &&
				inAnnouncement instanceof AnnouncementEntity) {

			final RequestEntity request = new RequestEntity(
					RequestStatus.PENDING, (TravellerEntity) inSender,
					(AnnouncementEntity) inAnnouncement);

			try {
				entityManager.persist(request);
			} catch(EntityExistsException | IllegalStateException |
					IllegalArgumentException |
					TransactionRequiredException ex) {
				throw new DataModificationException(
						"A problem occurred while trying to "
						+ "save the new request.", ex);
			}
		} else {
			throw new DataModificationException(
					"Could not save a new request beacause of wrong "
					+ "type of the announcement or traveller associated "
					+ "with it.");
		}
	}

	@Override
	protected Class<? extends RequestEntity> getEntityClass()
	{
		return RequestEntity.class;
	}
}
