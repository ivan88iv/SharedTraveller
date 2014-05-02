package org.ai.shared.traveller.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * The broadcast receiver is used to start the notifications service on device
 * boot-up
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context inContext, Intent inIntent)
	{
		final NotificationServiceConfigurator serviceConfigurator =
				new NotificationServiceConfigurator(inContext);
		serviceConfigurator.configure();
	}
}
