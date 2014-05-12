package org.ai.shared.traveller.notification.social.sms;

import org.ai.sharedtraveller.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

/**
 * The broadcast receiver listens for the sms delivery status changes
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class SmsDeliverReceiver extends BroadcastReceiver
{
	private static final String SMS_DELIVERED = "Sms delivered.";

	private static final String DELIVERY_ERROR = "Delivery error.";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		final Resources resources = context.getResources();

		switch (getResultCode())
		{
		case Activity.RESULT_OK:
			Log.d("SmsDeliverReceiver", SMS_DELIVERED);
			Toast.makeText(context,
					resources.getString(R.string.notification_sms_delivered),
					Toast.LENGTH_SHORT).show();
			break;
		case Activity.RESULT_CANCELED:
			Log.d("SmsDeliverReceiver", DELIVERY_ERROR);
			Toast.makeText(
					context,
					resources
							.getString(R.string.notification_sms_not_delivered),
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
