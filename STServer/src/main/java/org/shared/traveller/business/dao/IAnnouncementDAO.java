package org.shared.traveller.business.dao;

import java.util.Date;
import java.util.List;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.service.dto.GetAllAnnouncementsRequest;

public interface IAnnouncementDAO extends IDAO<IPersistentAnnouncement>
{

	List<? extends IPersistentAnnouncement> getAll(
			GetAllAnnouncementsRequest request);

	long getAllCount(GetAllAnnouncementsRequest request);

	/**
	 * The method loads an announcement with the given characteristics
	 * 
	 * @param inStartCity
	 *            the start city
	 * @param inEndCity
	 *            the end city
	 * @param inDepDate
	 *            the date of the departure
	 * @param inDriver
	 *            the name of the driver
	 * @return the announcement that is loaded or null if none is found
	 * 
	 * @throws DataExtractionException
	 *             when a problem occurs while trying to load the announcement
	 */
	IPersistentAnnouncement loadAnnouncement(
			final String inStartCity,
			final String inEndCity,
			final Date inDepDate,
			final String inDriver);

	/**
	 * The method loads an announcement with explicitly loading its requests by
	 * the announcement's id
	 * 
	 * @param inId
	 *            the id of the announcement. It may not be null.
	 * @return the loaded announcement or null if none exists
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while loading this announcement
	 */
	IPersistentAnnouncement loadAnnouncementWithRequests(final Long inId);
}
