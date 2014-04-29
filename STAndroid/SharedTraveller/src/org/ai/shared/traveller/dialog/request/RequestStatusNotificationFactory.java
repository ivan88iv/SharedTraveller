package org.ai.shared.traveller.dialog.request;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.NotificationDialogFactory;

import android.support.v4.app.FragmentActivity;

/**
 * The class represents a dialog factory for notification dialogs shown after
 * the status change of a request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestStatusNotificationFactory extends
		NotificationDialogFactory
{
	/**
	 * Instantiates a new request status dialog factory
	 * 
	 * @param inActivity
	 *            the activity to which the new dialog will be associated
	 * @param inTag
	 *            the tag with which the dialog fragment is associated
	 */
	public RequestStatusNotificationFactory(
			final FragmentActivity inActivity, final String inTag)
	{
		super(inActivity, inTag);
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.REQUEST_NOTIFICATION.getCode();
	}
}
