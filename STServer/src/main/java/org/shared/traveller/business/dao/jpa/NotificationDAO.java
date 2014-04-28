package org.shared.traveller.business.dao.jpa;

import org.shared.traveller.business.dao.INotificationDAO;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.jpa.NotificationEntity;
import org.springframework.stereotype.Repository;

/**
 * The class is responsible for the common access operations for
 * persistent notification instances
 *
 * @author "Ivan Ivanov"
 *
 */
@Repository
public class NotificationDAO
		extends AbstractDAO<IPersistentNotification> implements
		INotificationDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4623575584122154469L;

	@Override
	protected Class<? extends IPersistentNotification> getEntityClass()
	{
		return NotificationEntity.class;
	}
}
