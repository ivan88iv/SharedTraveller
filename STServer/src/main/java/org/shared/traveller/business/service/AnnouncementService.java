package org.shared.traveller.business.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.shared.traveller.business.dao.IAnnouncementDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
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
		try
		{
			announcementDAO.insert(inAnnouncement);
		} catch (Exception e)
		{
			// TODO handle exceptions
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method loads the announcement that has the given start and end
	 * points, the provided departure date and driver
	 * 
	 * @param inStartCity
	 *            the start point of the announcement
	 * @param inEndCity
	 *            the end point of the announcement
	 * @param inDepDate
	 *            the departure date for the announcement
	 * @param inDriver
	 *            the name of the driver
	 * @return an announcement that fits all the selected criteria or null if
	 *         none is found
	 * @throws DataExtractionException
	 *             if an error occurs while trying to extract the announcement
	 */
	public IPersistentAnnouncement loadAnnouncement(final String inStartCity,
			final String inEndCity,
			final Date inDepDate, final String inDriver)
			throws DataExtractionException
	{

		return announcementDAO.loadAnnouncement(inStartCity, inEndCity,
				inDepDate, inDriver);
	}

	/**
	 * Returns the count of the announcements from the persistent layer that
	 * correspond to the specified information
	 * 
	 * @param request
	 *            the information needed for extracting all announcements
	 * @return the count of the announcements in the database
	 */
	public long getAllAnnouncementsCount(GetAllAnnouncementsRequest request)
	{
		return announcementDAO.getAllCount(request);
	}

	/**
	 * Returns a list of all persistent announcements that relate to the
	 * specified information
	 * 
	 * @param request
	 *            the information needed to extract
	 * @return
	 */
	public List<? extends IPersistentAnnouncement> getAllAnnouncements(
			GetAllAnnouncementsRequest request)
	{
		return announcementDAO.getAll(request);
	}
}
