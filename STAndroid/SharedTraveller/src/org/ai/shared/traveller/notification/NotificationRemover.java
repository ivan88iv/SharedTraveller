package org.ai.shared.traveller.notification;

import java.util.List;

import org.shared.traveller.utility.InstanceAsserter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

/**
 * The class is responsible for removing the android UI notifications from
 * inside a specific activity
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationRemover
{
	private final Activity activity;

	/**
	 * Creates a new notification remover instance
	 * 
	 * @param inActivity
	 *            the activity for which the remover is created. It may not be
	 *            null
	 */
	public NotificationRemover(final Activity inActivity)
	{
		InstanceAsserter.assertNotNull(inActivity, "activity");

		activity = inActivity;
	}

	/**
	 * The method removes all UI notifications currently coming from the
	 * application if we have come to the activity for which the current remover
	 * is responsible not by clicking a notification in the status bar.
	 * Otherwise only the related notifications are removed from the status bar.
	 */
	public void removeNotifications()
	{
		final Intent currIntent = activity.getIntent();
		final NotifiedIntentManager marker = NotifiedIntentManager
				.getInstance();
		final NotificationManager manager =
				(NotificationManager) activity.getSystemService(
						Context.NOTIFICATION_SERVICE);
		if (marker.isMarked(currIntent))
		{
			final List<NotificationIdentifier> notifsToCancel =
					marker.getIdenticalActiveNotifs(currIntent);
			clearNotification(manager, notifsToCancel);
		} else
		{
			manager.cancelAll();
		}
	}

	/**
	 * The method removes all notifications that are designated by the list of
	 * identifiers from the status bar
	 * 
	 * @param inManager
	 *            the manager used for removing the notifications. It may not be
	 *            null
	 * @param inNotifsToCancel
	 *            the identifiers which determine the notifications to be
	 *            removed
	 */
	private void clearNotification(final NotificationManager inManager,
			final List<NotificationIdentifier> inNotifsToCancel)
	{
		if (inNotifsToCancel != null)
		{
			for (NotificationIdentifier currIdent : inNotifsToCancel)
			{
				final String tag = currIdent.getTag();
				final int requestCode = currIdent.getRequestCode();
				inManager.cancel(tag, requestCode);
			}
		}
	}
}
