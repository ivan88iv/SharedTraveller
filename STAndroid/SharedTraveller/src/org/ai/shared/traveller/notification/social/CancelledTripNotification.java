package org.ai.shared.traveller.notification.social;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;

/**
 * The class represents a social notification sent after a trip has been
 * cancelled
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class CancelledTripNotification implements ISocialNotification
{
	private final String message;

	/**
	 * Instantiates a new trip cancellation notification
	 * 
	 * @param inContext
	 *            the context into which the notification is created. It may not
	 *            be null
	 * @param inTrip
	 *            the trip which is cancelled. It may not be null
	 */
	public CancelledTripNotification(final Context inContext,
			final IRequestedAnnouncement inTrip)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inTrip, "cancelled trip");

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		final String departureDate = dateFormat.format(inTrip
				.getDepartureDate());
		message = MessageFormat.format(
				inContext.getResources().getString(
						R.string.notification_cancelled_trip),
				inTrip.getDriver(), inTrip.getFrom(), inTrip.getTo(),
				departureDate);
	}

	@Override
	public String getMessage()
	{
		return message;
	}
}
