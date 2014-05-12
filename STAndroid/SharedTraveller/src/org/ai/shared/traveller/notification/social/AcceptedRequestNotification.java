package org.ai.shared.traveller.notification.social;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;

/**
 * The class represents a social notification for an accepted travel request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class AcceptedRequestNotification implements ISocialNotification
{
	private final String message;

	/**
	 * Creates a new social notification for an accepted travel request
	 * 
	 * @param inContext
	 *            the context into which the notification is sent. It may not be
	 *            null
	 * @param inAnnouncementInfo
	 *            the announcement information associated with the accepted
	 *            request. It may not be null
	 */
	public AcceptedRequestNotification(final Context inContext,
			final IRequestedAnnouncement inAnnouncementInfo)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inAnnouncementInfo,
				"announcement information");

		final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		message = MessageFormat.format(inContext.getResources().getString(
				R.string.notification_accepted_request),
				inAnnouncementInfo.getDriver(), inAnnouncementInfo.getFrom(),
				inAnnouncementInfo.getTo(),
				formatter.format(inAnnouncementInfo.getDepartureDate()));
	}

	@Override
	public String getMessage()
	{
		return message;
	}

}
