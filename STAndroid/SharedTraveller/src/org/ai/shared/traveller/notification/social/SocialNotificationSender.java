package org.ai.shared.traveller.notification.social;

import org.ai.sharedtraveller.R;
import org.shared.traveller.utility.InstanceAsserter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * The class sends notifications via systems outside the application such as sms
 * or email
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class SocialNotificationSender
{
	private final Context context;

	private final ISocialNotification notification;

	/**
	 * Creates a new notification sender
	 * 
	 * @param inContext
	 *            the context for sending notifications. It may not be null
	 * @param inNotification
	 *            the notification to be sent. It may not be null
	 */
	public SocialNotificationSender(final Context inContext,
			final ISocialNotification inNotification)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inNotification, "notification");

		context = inContext;
		notification = inNotification;
	}

	/**
	 * The method notifies the user via sms or email
	 * 
	 * @param inPhoneNumber
	 *            the phone number for sending the sms notification. If null no
	 *            sms will be sent.
	 * @param inReceiverId
	 *            the email address for sending the notification. If null no
	 *            email will be sent
	 */
	public void send(final String inPhoneNumber, final Long inReceiverId)
	{
		if (null != inPhoneNumber)
		{
			sendSms(inPhoneNumber, notification.getMessage());
		}

		if (null != inReceiverId)
		{
			sendEmail(inReceiverId, notification.getMessage());
		}
	}

	/**
	 * The method sends an sms to a user
	 * 
	 * @param inPhoneNumber
	 *            the phone number to receive the sms
	 * @param inMessage
	 *            the message to be sent
	 */
	private void sendSms(final String inPhoneNumber, final String inMessage)
	{
		final String appPackage = context.getApplicationInfo().packageName;
		final String SENT = appPackage + ".SMS_SENT";
		final String DELIVERED = appPackage + ".SMS_DELIVERED";

		final PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
				new Intent(SENT), 0);

		final PendingIntent deliveredPI = PendingIntent.getBroadcast(context,
				0, new Intent(DELIVERED), 0);

		final SmsManager manager = SmsManager.getDefault();
		try
		{
			manager.sendTextMessage(inPhoneNumber, null, inMessage, sentPI,
					deliveredPI);
		} catch (final Exception e)
		{
			Log.e("SmsSender", e.getMessage());
			Toast.makeText(context, context.getResources()
					.getString(R.string.notification_sms_error),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * The method starts a service which sends the specified message to the
	 * specified recipient
	 * 
	 * @param inReceiverId
	 *            the id of the message receiver. It may not be null
	 * @param inMessage
	 *            the message to be sent. It may not be null
	 */
	private void sendEmail(final Long inReceiverId, final String inMessage)
	{
		final Intent intent = new Intent(context, MailingService.class);
		intent.putExtra(MailingService.MESSAGE, inMessage);
		intent.putExtra(MailingService.RECEIVER, String.valueOf(inReceiverId));

		context.startService(intent);
	}
}
