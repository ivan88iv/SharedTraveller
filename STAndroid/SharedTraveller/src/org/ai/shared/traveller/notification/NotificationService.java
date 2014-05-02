package org.ai.shared.traveller.notification;

import org.ai.shared.traveller.network.connection.AbstractNetworkService;
import org.ai.shared.traveller.network.connection.task.notification.NotificationsExtractionTask;

import android.content.Intent;
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
}
