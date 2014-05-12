package org.ai.shared.traveller.notification.social;

import org.ai.shared.traveller.network.connection.AbstractNetworkService;
import org.ai.shared.traveller.network.connection.task.notification.SendEmailNotificationTask;
import org.ai.sharedtraveller.R;

import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * The service is responsible for sending email messages to a specific recipient
 * from the server side
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class MailingService extends AbstractNetworkService
{
	/**
	 * The key for the email message included in the intent with which the
	 * service is started. It is the key for the email message to be sent to the
	 * server
	 */
	public static final String MESSAGE = "msg";

	/**
	 * The key for the email's recipient included in the intent with which the
	 * service is started. It is the key for the receiver of the message sent
	 */
	public static final String RECEIVER = "receiver";

	private static final String MAIL_TASK = "mailTask";

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		final String emailMessage = intent.getStringExtra(MESSAGE);
		final Long receiverId = Long.valueOf(intent.getStringExtra(RECEIVER));

		addTask(MAIL_TASK, new SendEmailNotificationTask(this, emailMessage,
				receiverId));
		executeTask(MAIL_TASK);

		return START_NOT_STICKY;
	}

	/**
	 * The method is executed when the email notification has been successfully
	 * sent
	 */
	public void onSuccessfulMailSend()
	{
		final String message = getResources().getString(
				R.string.notification_email_notification_ok);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * The method is executed when the email notification has not been sent
	 * because of a problem
	 */
	public void onErrorMailSend()
	{
		final String message = getResources().getString(
				R.string.notification_email_notification_error);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
