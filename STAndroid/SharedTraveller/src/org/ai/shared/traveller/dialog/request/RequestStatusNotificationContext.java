package org.ai.shared.traveller.dialog.request;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.NotificationDialogContext;

import android.support.v4.app.FragmentActivity;

/**
 * The class represents a context for notification dialogs shown after the
 * status change of a request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestStatusNotificationContext extends
		NotificationDialogContext
{
	/**
	 * Instantiates a new request-status-dialog context
	 * 
	 * @param inActivity
	 *            the activity to which the new dialog will be associated
	 * @param inTag
	 *            the tag with which the dialog fragment is associated
	 * @param inEnableSmsCheckbox
	 *            true if the sms notifications checkbox is enabled
	 * @param inEnableEmainCheckbox
	 *            true if the email notifications checkbox is enabled
	 */
	public RequestStatusNotificationContext(
			final FragmentActivity inActivity, final String inTag,
			final boolean inEnableSmsCheckbox,
			final boolean inEnableEmainCheckbox)
	{
		super(inActivity, inTag, inEnableSmsCheckbox, inEnableEmainCheckbox);
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.REQUEST_NOTIFICATION.getCode();
	}
}
