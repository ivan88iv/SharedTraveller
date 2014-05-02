package org.ai.shared.traveller.notification;

import org.shared.traveller.utility.InstanceAsserter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;

/**
 * The class is responsible for preparing the service for extracting
 * notifications from the server side. The class configures when and how often
 * the service is called.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationServiceConfigurator
{
	private static final long[] INTERVAL_OPTIONS =
	{
			AlarmManager.INTERVAL_FIFTEEN_MINUTES,
			AlarmManager.INTERVAL_HALF_HOUR,
			AlarmManager.INTERVAL_HOUR,
			AlarmManager.INTERVAL_HALF_DAY,
			AlarmManager.INTERVAL_DAY
	};

	/**
	 * The initial offset in milliseconds for the service to be called the first
	 * time it is configured.
	 */
	private static final long INITIAL_OFFSET = 5000;

	private final Context context;

	private final long interval;

	/**
	 * The constructor instantiates a new configurator object
	 * 
	 * @param inContext
	 *            the android context used for running the notification service.
	 *            It may not be null.
	 */
	public NotificationServiceConfigurator(final Context inContext)
	{
		InstanceAsserter.assertNotNull(inContext, "context");

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(inContext);
		int intervalOptionInd = Integer.valueOf(
				prefs.getString("notification_intervals", "3")).intValue();

		context = inContext;
		interval = INTERVAL_OPTIONS[intervalOptionInd];
	}

	/**
	 * The method configures how often and when the notification service should
	 * be called
	 */
	public void configure()
	{
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(
				Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotificationService.class);
		PendingIntent notificationIntent = PendingIntent.getService(
				context, 0, intent, 0);

		alarmManager.cancel(notificationIntent);
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime() + INITIAL_OFFSET,
				interval, notificationIntent);
	}
}
