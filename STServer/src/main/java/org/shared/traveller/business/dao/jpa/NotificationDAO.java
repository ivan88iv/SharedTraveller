package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;
import java.util.ArrayList;
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

	private static final String NOTIFICATION_REMOVAL_PROBLEM =
			"A problem occurred while trying to remove notifications with ids {0}.";

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
	public void remove(List<? extends IPersistentNotification> inNotifications)
	{
		InstanceAsserter.assertNotNull(inNotifications, "notifications");

		if (inNotifications.isEmpty())
		{
			return;
		}

		final List<Long> notificationIds = new ArrayList<>();

		for (final IPersistentNotification currNot : inNotifications)
		{
			notificationIds.add(currNot.getId());
		}

		final DataWriter writer = new DataWriter()
		{
			@Override
			protected void prepareQuery(Query inQuery)
			{
				inQuery.setParameter("notificationIds", notificationIds);
			}
		};
		writer.execute(RequestNamedQueryNames.REMOVE_NOTIFICATIONS,
				entityManager, MessageFormat.format(
						NOTIFICATION_REMOVAL_PROBLEM, notificationIds));
	}

	@Override
	protected Class<? extends IPersistentNotification> getEntityClass()
	{
		return NotificationEntity.class;
	}
}
