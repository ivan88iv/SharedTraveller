package org.ai.shared.traveller.call;

import org.ai.shared.traveller.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * The class is responsible for ending a call started from within the
 * application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class CallEnder extends PhoneStateListener
{
	private boolean offHooked = false;

	private final Context context;

	/**
	 * Instantiates a new call ender instance
	 * 
	 * @param inContext
	 *            the context to which the ender is associated
	 */
	public CallEnder(final Context inContext)
	{
		super();

		context = inContext;
	}

	@Override
	public void onCallStateChanged(final int inState,
			final String inIncomingNumber)
	{
		if (inState == TelephonyManager.CALL_STATE_OFFHOOK)
		{
			Log.i("CallEnder", "A call is hooked off.");
			offHooked = true;
		} else if (inState == TelephonyManager.CALL_STATE_IDLE &&
				offHooked)
		{
			offHooked = false;
			Log.i("CallEnder", "The call has ended.");

			final SharedPreferences callPreferences =
					context.getSharedPreferences("call_prefs",
							Context.MODE_PRIVATE);
			final String beforeCallActivityClassName =
					callPreferences.getString("lastActivity",
							MainActivity.class.getName());
			Class<?> beforeCallActivityClass = null;

			try
			{
				beforeCallActivityClass =
						Class.forName(beforeCallActivityClassName);
			} catch (final ClassNotFoundException cnfe)
			{
				beforeCallActivityClass = MainActivity.class;
			}

			final Intent appLauchIntent = new Intent(context,
					beforeCallActivityClass);
			appLauchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(appLauchIntent);
		}
	}
}
