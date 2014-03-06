package org.shared.traveller.business.service;

import java.io.Serializable;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.dao.IRequestDAO;
import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents a business service responsible
 * for the business actions concerning request instances
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

	@Autowired
	private IAnnouncementDAO announcementDAO;

	@Autowired
	private ITravellerDAO travellerDAO;

	@Autowired
	private IRequestDAO requestDAO;

	/**
	 * The methods creates a new request, sent by a specific
	 * user for a specific announcement.
	 *
	 * @param inAnnouncement the announcement for which a new
	 * request is created
	 * @param inSender the user that sent the request
	 *
	 * @throws DataModificationException if an error occurs while
	 * trying to save the new request
	 */
	public void createNewRequest(final IPersistentAnnouncement inAnnouncement,
			final IPersistentTraveller inSender)
	throws DataModificationException {
		requestDAO.saveNewRequest(inSender, inAnnouncement);
	}
}
