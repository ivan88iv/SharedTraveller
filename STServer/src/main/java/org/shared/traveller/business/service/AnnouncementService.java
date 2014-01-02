package org.shared.traveller.business.service;

import java.io.Serializable;

import java.util.List;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.service.dto.GetAllAnnouncementsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

	public long getAllAnnouncementsCount(GetAllAnnouncementsRequest request)
	{
		return announcementDAO.getAllCount(request);
	}

	public List<? extends IPersistentAnnouncement> getAllAnnouncements(GetAllAnnouncementsRequest request)
	{
		return announcementDAO.getAll(request);
	}
}
