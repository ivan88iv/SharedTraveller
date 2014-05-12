package org.ai.shared.traveller.dialog;

import org.ai.shared.traveller.dialog.STDialogFragment.AbstractDialogContext;
import org.ai.sharedtraveller.R;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

/**
 * The class is responsible for the providing the context of dialogs that ask
 * the user for sending some additional notifications to the clients
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class NotificationDialogContext extends AbstractDialogContext
{
	private final View content;

	/**
	 * Instantiates a new dialog context
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
	public NotificationDialogContext(final FragmentActivity inActivity,
			final String inTag, final boolean inEnableSmsCheckbox,
			final boolean inEnableEmainCheckbox)
	{
		super(inActivity, inTag);

		content = inflateContent(inEnableSmsCheckbox, inEnableEmainCheckbox);
	}

	@Override
	protected int loadTitleResourceId()
	{
		return R.string.notification_dialog_title;
	}

	@Override
	protected String loadDescriptionMsg()
	{
		final String descriptionMsg = getActivity().getResources()
				.getString(R.string.notificaiton_dialog_sub_title);
		return descriptionMsg;
	}

	@Override
	protected int loadConfirmBtnResourceId()
	{
		return R.string.notification_dialog_confirmation_btn;
	}

	@Override
	protected int loadRejectBtnResourceId()
	{
		return R.string.notification_dialog_rejection_btn;
	}

	@Override
	protected View loadContent()
	{
		return content;
	}

	/**
	 * Returns true if the sms-notification checkbox is selected
	 * 
	 * @return true if the sms-notification checkbox is selected
	 */
	public boolean isSmsNotificationOn()
	{
		final CheckBox smsCheckbox = (CheckBox)
				content.findViewById(R.id.notification_sms);

		return smsCheckbox.isChecked();
	}

	/**
	 * Returns true if the email-notification checkbox is selected
	 * 
	 * @return true if the email-notification checkbox is selected
	 */
	public boolean isEmailNotificationOn()
	{
		final CheckBox emailCheckbox = (CheckBox)
				content.findViewById(R.id.notification_email);

		return emailCheckbox.isChecked();
	}

	/**
	 * Inflates the content specific for the notification dialogs
	 * 
	 * @param inEnableSmsCheckbox
	 *            true if sms notifications are enabled
	 * @param inEnableEmailCheckbox
	 *            true if email notificaitons are enabled
	 * @return the inflated content
	 */
	private View inflateContent(final boolean inEnableSmsCheckbox,
			final boolean inEnableEmailCheckbox)
	{
		final LayoutInflater inflater =
				getActivity().getLayoutInflater();
		View content = inflater.inflate(
				R.layout.notification_dialog_content, null);
		content.findViewById(R.id.notification_sms).setEnabled(
				inEnableSmsCheckbox);
		content.findViewById(R.id.notification_email).setEnabled(
				inEnableEmailCheckbox);
		return content;
	}
}
