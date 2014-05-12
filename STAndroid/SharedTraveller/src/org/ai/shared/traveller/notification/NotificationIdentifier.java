package org.ai.shared.traveller.notification;

import java.io.Serializable;

import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.EqualityChecker;
import org.shared.traveller.utility.HashCodeImplementer;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class identifies different UI notifications
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationIdentifier implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -7583346973293521629L;

	private static final String NEW_REQUEST_NOTIF_TAG_PREF =
			"new request for announcement ";

	private static final String DECLINED_REQUEST_NOTIF_TAG_PREF =
			"declined request for announcement ";

	private static final String TRAVEL_NOTIF_TAG_PREF =
			"travel for announcement ";

	private final Type notificationType;

	private final Long announcementId;

	private transient String tag;

	private transient Integer requestCode;

	/**
	 * Creates a new notification identifier
	 * 
	 * @param inNotificationType
	 *            the type of the notification for which this identifier is
	 *            used. It may not be null
	 * @param inAnnouncementId
	 *            the id of the announcement for this notification. It may not
	 *            be null
	 */
	public NotificationIdentifier(final Type inNotificationType,
			final Long inAnnouncementId)
	{
		InstanceAsserter.assertNotNull(inNotificationType, "notification type");
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement's id");

		notificationType = inNotificationType;
		announcementId = inAnnouncementId;
	}

	public Type getNotificationType()
	{
		return notificationType;
	}

	public Long getAnnouncementId()
	{
		return announcementId;
	}

	public String getTag()
	{
		if (null == tag)
		{
			switch (notificationType)
			{
			case NEW_REQUEST:
				tag = NEW_REQUEST_NOTIF_TAG_PREF + announcementId;
				break;
			case REQUEST_DECLINATION:
				tag = DECLINED_REQUEST_NOTIF_TAG_PREF + announcementId;
				break;
			case REQUEST_ACCEPTANCE:
			case REQUEST_REJECTION:
			case TRIP_CANCELLATION:
				tag = TRAVEL_NOTIF_TAG_PREF + announcementId;
				break;
			default:
				tag = null;
				break;
			}
		}

		return tag;
	}

	public int getRequestCode()
	{
		if (null == requestCode)
		{
			requestCode = Integer.valueOf(0);
		}

		return requestCode;
	}

	/**
	 * The method checks if the specified identifier is similar to the current
	 * one. Similarity between identifiers is used for canceling notifications
	 * with identical identifiers
	 * 
	 * @param inIdentifier
	 *            the identifier that is checked. It may not be null
	 * @return true if the identifier is identical to the current one
	 */
	public boolean isIdentical(final NotificationIdentifier inIdentifier)
	{
		InstanceAsserter.assertNotNull(inIdentifier, "identifier");

		final Type compareType = inIdentifier.notificationType;
		final Long compareAnnouncementId = inIdentifier.announcementId;

		if (notificationType == Type.NEW_REQUEST ||
				notificationType == Type.REQUEST_DECLINATION)
		{
			return announcementId.equals(compareAnnouncementId) &&
					(compareType == Type.NEW_REQUEST ||
					compareType == Type.REQUEST_DECLINATION);
		} else
		{
			return compareType == Type.REQUEST_ACCEPTANCE ||
					compareType == Type.REQUEST_REJECTION ||
					compareType == Type.TRIP_CANCELLATION;
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof NotificationIdentifier))
		{
			return false;
		}

		final NotificationIdentifier compareIdentifier =
				(NotificationIdentifier) o;

		return EqualityChecker.areEqual(notificationType,
				compareIdentifier.notificationType)
				&& EqualityChecker.areEqual(announcementId,
						compareIdentifier.announcementId);
	}

	@Override
	public int hashCode()
	{
		final HashCodeImplementer implementer = new HashCodeImplementer();
		return implementer.consider(notificationType).consider(announcementId)
				.calculate();
	}
}
