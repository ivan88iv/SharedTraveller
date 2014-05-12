package org.ai.shared.traveller.notification.social.sms;

import org.ai.sharedtraveller.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * The receiver provides information about the (un)successful sending of the
 * social sms notifications
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class SmsSenderReceiver extends BroadcastReceiver
{
	private static final String SENT_OK =
			"Sms sent successfully.";

	private static final String RESULT_ERROR_GENERIC_FAILURE =
			"Generic failure.";

	private static final String NO_SERVICE = "No sms service available.";

	private static final String NO_PDU = "No PDU available.";

	private static final String NO_RADIO = "No radio signal.";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		final Resources resources = context.getResources();

		switch (getResultCode())
		{
		case Activity.RESULT_OK:
			// Intentionally left blank. The user should be notified on
			// successful sms delivery only
			Log.d("SmsSenderReceiver", SENT_OK);
			break;
		case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
			Log.d("SmsSenderReceiver", RESULT_ERROR_GENERIC_FAILURE);
			Toast.makeText(context,
					resources.getString(R.string.notification_sms_error),
					Toast.LENGTH_LONG).show();
			break;
		case SmsManager.RESULT_ERROR_NO_SERVICE:
			Log.d("SmsSenderReceiver", NO_SERVICE);
			Toast.makeText(context, R.string.notification_no_sms_service,
					Toast.LENGTH_LONG).show();
			break;
		case SmsManager.RESULT_ERROR_NULL_PDU:
			Log.d("SmsSenderReceiver", NO_PDU);
			Toast.makeText(context, "Null PDU",
					Toast.LENGTH_LONG).show();
			break;
		case SmsManager.RESULT_ERROR_RADIO_OFF:
			Log.d("SmsSenderReceiver", NO_RADIO);
			Toast.makeText(context, R.string.notification_sms_radio_turned_off,
					Toast.LENGTH_LONG).show();
			break;
		}
	}
}
