package org.shared.traveller.business.dao;

import java.util.List;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.service.dto.GetAllAnnouncementsRequest;

public interface IAnnouncementDAO extends IDAO<IPersistentAnnouncement>
{

	public List<? extends IPersistentAnnouncement> getAll(GetAllAnnouncementsRequest request);

	public long getAllCount(GetAllAnnouncementsRequest request);

}
