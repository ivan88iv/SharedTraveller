package org.ai.shared.traveller.notification;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.ai.shared.traveller.trip.MyTripsActivity;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.client.domain.visitor.INotificationVisitor;
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * The class is responsible for showing notifications in the android system
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class UINotificationSender implements INotificationVisitor
{
	private final Context context;

	private final Bitmap largeIcon;

	private final List<NotificationIdentifier> activeNotifIdentifiers;

	/**
	 * Creates a new notification sender instance
	 * 
	 * @param inContext
	 *            the context which is used to send the notifications. It may
	 *            not be null
	 * @param inLargeIcon
	 *            the large icon to be displayed for all notifications shown. It
	 *            is only displayed in android APIs 11+
	 * @param inActiveIdentifiers
	 *            the identifiers of the currently active notifications. It may
	 *            not be null
	 */
	public UINotificationSender(final Context inContext,
			final Bitmap inLargeIcon,
			final List<NotificationIdentifier> inActiveIdentifiers)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inActiveIdentifiers,
				"active notification identifiers");

		context = inContext;
		largeIcon = inLargeIcon;
		activeNotifIdentifiers = DeepCopier.copy(inActiveIdentifiers);
	}

	@Override
	public void visit(final INotification inNotificationData)
	{
		final NotificationCompat.Builder builder =
				new NotificationCompat.Builder(context);
		final NotificationIdentifier identifier = new NotificationIdentifier(
				inNotificationData.getType(),
				inNotificationData.getAnnouncementId());
		final PendingIntent pendingIntent = constructIntent(identifier,
				inNotificationData);
		final int smallIcon = prepareSmallIcon(inNotificationData);

		final Notification builtNotification = builder
				.setContentTitle(inNotificationData.getTitle())
				.setContentText(inNotificationData.getDescription())
				.setSmallIcon(smallIcon)
				.setLargeIcon(largeIcon)
				.setTicker(inNotificationData.getTitle() + "\n"
						+ inNotificationData.getDescription())
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND
						| Notification.DEFAULT_VIBRATE)
				.setWhen(inNotificationData.getCreationDate().getTime())
				.setContentIntent(pendingIntent).build();

		sendNotification(inNotificationData, builtNotification, identifier);
	}

	private void sendNotification(final INotification inNotificationData,
			final Notification inBuiltNotification,
			final NotificationIdentifier inIdentifier)
	{
		final NotificationManager manager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(inIdentifier.getTag(), inIdentifier.getRequestCode(),
				inBuiltNotification);
	}

	private int prepareSmallIcon(final INotification inNotification)
	{
		int smallIcon;

		switch (inNotification.getType())
		{
		case NEW_REQUEST:
			smallIcon = R.drawable.new_request;
			break;
		case REQUEST_DECLINATION:
			smallIcon = R.drawable.declined_request;
			break;
		case REQUEST_ACCEPTANCE:
			smallIcon = R.drawable.approved_request;
			break;
		case REQUEST_REJECTION:
			smallIcon = R.drawable.rejected_request;
			break;
		default:
			smallIcon = R.drawable.cancelled_trip;
			break;
		}

		return smallIcon;
	}

	private PendingIntent constructIntent(
			final NotificationIdentifier inIdentifier,
			final INotification inNotification)
	{
		final Type notifType = inNotification.getType();
		Intent intent = null;
		final TaskStackBuilder builder = TaskStackBuilder.create(context);
		Class<? extends Activity> activityClass = null;

		if (notifType == Type.NEW_REQUEST ||
				notifType == Type.REQUEST_DECLINATION)
		{
			intent = new Intent(context,
					AnnouncementRequestActivity.class);
			intent.putExtra(AnnouncementRequestActivity.ANNOUNCEMENT_ID_KEY,
					inNotification.getAnnouncementId());
			intent.setAction(
					"org.ai.sharedtraveller.REQUESTS_FOR_ANNOUNCEMENT_WITH_ID_"
							+ inNotification.getAnnouncementId());
			activityClass = AnnouncementRequestActivity.class;
		} else
		{
			intent = new Intent(context, MyTripsActivity.class);
			activityClass = MyTripsActivity.class;
		}

		final NotifiedIntentManager marker = NotifiedIntentManager
				.getInstance();
		marker.mark(intent);
		marker.setIdenticalActiveNotifs(intent,
				prepareIdenticalActiveNotifs(inIdentifier));
		builder.addParentStack(activityClass);
		builder.addNextIntent(intent);
		return builder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	private List<NotificationIdentifier> prepareIdenticalActiveNotifs(
			final NotificationIdentifier inCurrIdentifier)
	{
		final List<NotificationIdentifier> identicalNotifications =
				new ArrayList<NotificationIdentifier>();
		for (NotificationIdentifier activeIdentifier : activeNotifIdentifiers)
		{
			if (!inCurrIdentifier.equals(activeIdentifier)
					&& inCurrIdentifier.isIdentical(activeIdentifier))
			{
				identicalNotifications.add(activeIdentifier);
			}
		}

		return identicalNotifications;
	}
}
