package org.ai.shared.traveller.dialog.request;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.NotificationDialogContext;

import android.support.v4.app.FragmentActivity;

public class RequestCreationNotificationContext extends
		NotificationDialogContext
{

	public RequestCreationNotificationContext(
			final FragmentActivity inActivity,
			final boolean inEnableSmsCheckbox,
			final boolean inEnableEmainCheckbox)
	{
		super(inActivity, "new_request_notification", inEnableSmsCheckbox,
				inEnableEmainCheckbox);
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.REQUEST_CREATION_NOTIFICATION.getCode();
	}
}
