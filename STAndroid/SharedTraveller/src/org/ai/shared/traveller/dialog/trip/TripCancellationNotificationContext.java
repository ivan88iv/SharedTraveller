package org.ai.shared.traveller.dialog.trip;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.NotificationDialogContext;

import android.support.v4.app.FragmentActivity;

/**
 * The class represents a dialog context used for notification dialogs shown
 * after a trip has been cancelled
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class TripCancellationNotificationContext extends
		NotificationDialogContext
{
	/**
	 * Instantiates a new trip-cancellation-dialog context
	 * 
	 * @param inActivity
	 *            the activity to which the new dialog will be associated
	 * @param inTag
	 *            the tag with which the dialog fragment is associated
	 */
	public TripCancellationNotificationContext(
			final FragmentActivity inActivity, final String inTag)
	{
		super(inActivity, inTag, true, true);
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION.getCode();
	}
}
