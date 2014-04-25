package org.ai.shared.traveller.call;

import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

/**
 * The class represents the common functionality for activities from which the
 * call functionality may be invoked
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class CallerActivity extends AbstractNetworkActivity
{
	private static final String NULL_PHONE_NUMBER =
			"The phone number may not be null.";

	/**
	 * The key for the lastly visited activity before the call was made
	 */
	public static final String LAST_VISITED_ACTIVITY_KEY =
			"lastActivity";

	/**
	 * The method makes a call to the specified telephone number
	 * 
	 * @param inPhoneNumber
	 *            the telephone to call. It may not be null.
	 */
	public void call(final String inPhoneNumber)
	{
		assert null != inPhoneNumber : NULL_PHONE_NUMBER;

		updateLastVisitedActivity();

		final Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + inPhoneNumber));
		startActivity(callIntent);
	}

	/**
	 * The method updates the lastly visited activity in the application
	 */
	private void updateLastVisitedActivity()
	{
		final SharedPreferences callPreferences =
				getSharedPreferences("call_prefs", Context.MODE_PRIVATE);
		final Editor editor = callPreferences.edit();
		editor.putString(LAST_VISITED_ACTIVITY_KEY, getClass().getName());
		editor.commit();
	}
}
