package org.shared.traveller.business.service;

import java.io.Serializable;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.springframework.beans.factory.annotation.Autowired;

public class AnnouncementService implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 5712558993456711924L;

	@Autowired
	private IAnnouncementDAO announcementDAO;

	public void createNewAnnouncement(IPersistentAnnouncement inAnnouncement)
	{
		announcementDAO.persist(inAnnouncement);
	}
}
