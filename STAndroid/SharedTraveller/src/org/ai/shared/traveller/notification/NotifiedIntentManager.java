package org.ai.shared.traveller.notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.shared.traveller.utility.InstanceAsserter;

import android.content.Intent;

/**
 * The class is responsible for managing intents coming from android
 * notifications
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotifiedIntentManager implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 7824220333062157618L;

	private static final String INTENT_MARKER = "NOTIFICATION_INTENT";

	private static final String IDENTICAL_NOTIFICATIONS =
			"org.ai.sharedtraveller.IdenticalNotifications";

	private static final NotifiedIntentManager INSTANCE =
			new NotifiedIntentManager();

	/**
	 * The method returns a notified intent manager instance
	 * 
	 * @return a notified intent manager instance
	 */
	public static NotifiedIntentManager getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The class is not meant to be initialized through constructors. There is a
	 * special utility method for this reason.
	 */
	private NotifiedIntentManager()
	{
	}

	/**
	 * The method marks the intent as one that comes from a UI notification
	 * 
	 * @param inIntent
	 *            the intent to be marked. It may not be null
	 */
	public void mark(final Intent inIntent)
	{
		InstanceAsserter.assertNotNull(inIntent, "intent");

		inIntent.putExtra(INTENT_MARKER, true);
	}

	/**
	 * The method returns true if the intent has already been marked as one
	 * coming from a UI notification
	 * 
	 * @param inIntent
	 *            the intent to be checked. It may not be null
	 * @return true if the intent associated with the current instance has
	 *         already been marked as one coming from a UI notification
	 */
	public boolean isMarked(final Intent inIntent)
	{
		InstanceAsserter.assertNotNull(inIntent, "intent");

		return inIntent.getBooleanExtra(INTENT_MARKER, false);
	}

	public void setIdenticalActiveNotifs(final Intent inIntent,
			final List<NotificationIdentifier> inIdenticalIdentifiers)
	{
		InstanceAsserter.assertNotNull(inIdenticalIdentifiers,
				"identical identifiers");

		inIntent.putExtra(IDENTICAL_NOTIFICATIONS,
				new ArrayList<NotificationIdentifier>(inIdenticalIdentifiers));
	}

	@SuppressWarnings("unchecked")
	public List<NotificationIdentifier> getIdenticalActiveNotifs(
			final Intent inIntent)
	{
		return (List<NotificationIdentifier>) inIntent
				.getSerializableExtra(IDENTICAL_NOTIFICATIONS);
	}

	/**
	 * Used for deserialization purposes
	 * 
	 * @return the notified intent manager singleton instance
	 */
	private Object readResolve()
	{
		return INSTANCE;
	}
}
