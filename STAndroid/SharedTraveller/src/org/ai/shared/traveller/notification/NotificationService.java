package org.ai.shared.traveller.notification;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.network.connection.AbstractNetworkService;
import org.ai.shared.traveller.network.connection.task.notification.NotificationsExtractionTask;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * The service extracts new notifications for the current user
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationService extends AbstractNetworkService
{
	private static final String NOTIFICATIONS_EXTRACTION_TASK_KEY =
			"notificationsExtraction";

	private static final String LOCK_TAG = "notificaitonWakeLock";

	private WakeLock wakeLock;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent inIntent, int inFlags, int inStartId)
	{
		// obtain the wake lock
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_TAG);
		wakeLock.acquire();

		addTask(NOTIFICATIONS_EXTRACTION_TASK_KEY,
				new NotificationsExtractionTask(this, Long.valueOf(1)));
		executeTask(NOTIFICATIONS_EXTRACTION_TASK_KEY);

		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		wakeLock.release();
	}

	/**
	 * The method is called when the new notification instances have been
	 * successfully extracted from the service
	 * 
	 * @param inNotifications
	 *            the extracted notifications. It may not be null
	 */
	public void onSuccessfulExtraction(
			final List<? extends INotification> inNotifications)
	{
		InstanceAsserter.assertNotNull(inNotifications, "notifications");

		final Bitmap bigIcon = BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_launcher);

		final List<NotificationIdentifier> activeNotifIdentifiers =
				getActiveIdentifiers(inNotifications);

		for (INotification currNot : inNotifications)
		{
			final UINotificationSender sender = new UINotificationSender(this,
					bigIcon, activeNotifIdentifiers);
			currNot.accept(sender);
		}
	}

	private List<NotificationIdentifier> getActiveIdentifiers(
			final List<? extends INotification> inNotifications)
	{
		final List<NotificationIdentifier> identifiers =
				new ArrayList<NotificationIdentifier>();

		for (final INotification currNotf : inNotifications)
		{
			identifiers.add(new NotificationIdentifier(currNotf.getType(),
					currNotf.getAnnouncementId()));
		}

		return identifiers;
	}
}
