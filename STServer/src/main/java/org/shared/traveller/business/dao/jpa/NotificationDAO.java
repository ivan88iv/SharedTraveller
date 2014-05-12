package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.INotificationDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.dao.jpa.writer.DataWriter;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.jpa.NotificationEntity;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class is responsible for the common access operations for persistent
 * notification instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
@Repository
public class NotificationDAO extends AbstractDAO<IPersistentNotification>
		implements INotificationDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -4623575584122154469L;

	private static final String NOTIFICATION_LOAD_PROBLEM =
			"A problem occurred while trying to load notifications received by "
					+ "a customer with id {0}.";

	private static final String DRIVER_NOTIFICATION_REMOVAL_PROBLEM =
			"Could not remove the notifications for driver with id {0} and "
					+ "announcement with id {1}.";

	private static final String PASSENGER_NOTIFICAITON_REMOVAL_PROBLEM =
			"A problem occurred while trying to remove notifications for "
					+ "passenger with id {0}.";

	@Override
	public List<? extends IPersistentNotification> extractUserNotifications(
			final Long inUserId)
	{
		final DataExtractor<NotificationEntity> extractor =
				new DataExtractor<NotificationEntity>()
				{
					@Override
					protected void prepareQuery(
							TypedQuery<NotificationEntity> inQuery)
					{
						inQuery.setParameter("receiverId", inUserId);
					}
				};
		return extractor.execute(
				RequestNamedQueryNames.LOAD_USER_NOTIFICATIONS, entityManager,
				NotificationEntity.class,
				MessageFormat.format(NOTIFICATION_LOAD_PROBLEM, inUserId));
	}

	@Transactional
	@Override
	public void removeDriverNotifications(final Long inDriverId,
			final Long inAnnouncementId)
	{
		InstanceAsserter.assertNotNull(inDriverId, "driver's id");
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement's id");

		final DataWriter writer = new DataWriter()
		{
			@Override
			protected void prepareQuery(Query inQuery)
			{
				inQuery.setParameter("driverId", inDriverId)
						.setParameter("announcementId", inAnnouncementId);
			}
		};
		writer.execute(RequestNamedQueryNames.REMOVE_DRIVER_NOTIFICATIONS,
				entityManager, MessageFormat.format(
						DRIVER_NOTIFICATION_REMOVAL_PROBLEM, inDriverId,
						inAnnouncementId));
	}

	@Transactional
	@Override
	public void removePassengerNotifications(final Long inPassengerId)
	{
		InstanceAsserter.assertNotNull(inPassengerId, "passenger's id");

		final DataWriter writer = new DataWriter()
		{
			@Override
			protected void prepareQuery(Query inQuery)
			{
				inQuery.setParameter("passengerId", inPassengerId);
			}
		};
		writer.execute(RequestNamedQueryNames.REMOVE_PASSENGER_NOTIFICATIONS,
				entityManager, MessageFormat.format(
						PASSENGER_NOTIFICAITON_REMOVAL_PROBLEM, inPassengerId));
	}

	@Override
	protected Class<? extends IPersistentNotification> getEntityClass()
	{
		return NotificationEntity.class;
	}
}
