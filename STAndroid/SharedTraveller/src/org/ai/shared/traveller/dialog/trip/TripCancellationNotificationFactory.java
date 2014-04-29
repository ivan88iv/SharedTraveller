package org.ai.shared.traveller.dialog.trip;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.NotificationDialogFactory;

import android.support.v4.app.FragmentActivity;

/**
 * The class represents a dialog factory class responsible for creating
 * notification dialogs shown after a trip has been cancelled
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class TripCancellationNotificationFactory extends
		NotificationDialogFactory
{
	/**
	 * Instantiates a new trip cancellation dialog factory
	 * 
	 * @param inActivity
	 *            the activity to which the new dialog will be associated
	 * @param inTag
	 *            the tag with which the dialog fragment is associated
	 */
	public TripCancellationNotificationFactory(
			final FragmentActivity inActivity, final String inTag)
	{
		super(inActivity, inTag);
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION.getCode();
	}
}
