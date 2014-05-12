package org.shared.traveller.business.domain.visitor.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.visitor.IPersistentNotificationVisitor;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class is used to prepare a suitable descriptive information such as title
 * and description for the notifications that are being processed by it.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationDescriptor implements IPersistentNotificationVisitor
{
	private final IPersistentGenericNotification genericData;

	private String description;

	private String title;

	private final int accumulatedNotificationCnt;

	/**
	 * Creates a new notification descriptor instance
	 * 
	 * @param inGenericData
	 *            the generic notification data used for describing the
	 *            notifications. It may not be null.
	 * 
	 */
	public NotificationDescriptor(
			final IPersistentGenericNotification inGenericData)
	{
		this(inGenericData, 1);
	}

	/**
	 * Creates a new notification descriptor instance
	 * 
	 * @param inGenericData
	 *            the generic notification data used for describing the
	 *            notifications. It may not be null.
	 * @param inAccumulatedNotifications
	 *            the number of accumulated notifications which should be
	 *            described by this notification descriptor
	 */
	public NotificationDescriptor(
			final IPersistentGenericNotification inGenericData,
			final int inAccumulatedNotifications)
	{
		InstanceAsserter.assertNotNull(inGenericData,
				"generic notification data");

		genericData = inGenericData;
		accumulatedNotificationCnt = inAccumulatedNotifications;
	}

	/**
	 * Returns the description that has been prepared from the last notification
	 * processing
	 * 
	 * @return the description that has been prepared from the last notification
	 *         processing
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Returns the title that has been prepared from the last notification
	 * processing
	 * 
	 * @return the title that has been prepared from the last notification
	 *         processing
	 */
	public String getTitle()
	{
		return title;
	}

	@Override
	public void visit(final IPersistentNotification inNotification)
	{
		final IPersistentAnnouncement announcement =
				inNotification.getAnnouncement();
		final String startPoint = announcement.getStartPoint().getName();
		final String endPoint = announcement.getEndPoint().getName();
		final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		final String departureDate = formatter.format(
				announcement.getDepartureDate());

		description = MessageFormat.format(genericData.getTemplate(),
				startPoint, endPoint, departureDate);
		if (accumulatedNotificationCnt > 1)
		{
			title = MessageFormat.format(genericData.getPluralTitle(),
					accumulatedNotificationCnt);
		} else
		{
			title = genericData.getSingularTitle();
		}
	}
}
