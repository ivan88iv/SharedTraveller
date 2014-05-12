package org.ai.shared.traveller.notification.social;

import java.text.MessageFormat;

import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;

/**
 * The class represents a social notification sent when a request is declined
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class DeclinedRequestNotification implements ISocialNotification
{
	private final String notificationMsg;

	/**
	 * Creates a new social notification for request declination
	 * 
	 * @param inContext
	 *            the context in which the notification is created. It may not
	 *            be null
	 * @param inRequest
	 *            the request which was declined. It may not be null
	 */
	public DeclinedRequestNotification(final Context inContext,
			final IRequestInfo inRequest)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inRequest, "request");

		notificationMsg = MessageFormat.format(inContext.getResources()
				.getString(R.string.notification_declined_request),
				inRequest.getSender(), inRequest.getFromPoint(),
				inRequest.getToPoint(), inRequest.getDepartureDate());
	}

	@Override
	public String getMessage()
	{
		return notificationMsg;
	}
}
