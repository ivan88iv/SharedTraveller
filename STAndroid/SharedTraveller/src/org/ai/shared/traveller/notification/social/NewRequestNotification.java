package org.ai.shared.traveller.notification.social;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;

/**
 * The class represents a new-request social notification sent to drivers that
 * has posted the requested announcement
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestNotification implements ISocialNotification
{
	private final String message;

	/**
	 * Creates a new-request notification instance
	 * 
	 * @param inContext
	 *            the context into which the notification is created and sent.
	 *            It may not be null
	 * @param inRequest
	 *            the request for which this notification is created. It may not
	 *            be null
	 */
	public NewRequestNotification(final Context inContext,
			final IRequestInfo inRequest)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inRequest, "request");

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		final String departureDate = dateFormat.format(inRequest
				.getDepartureDate());
		message = MessageFormat.format(
				inContext.getResources().getString(
						R.string.notification_cancelled_trip),
				inRequest.getSender(), inRequest.getFromPoint(),
				inRequest.getToPoint(), departureDate);
	}

	@Override
	public String getMessage()
	{
		return message;
	}
}
