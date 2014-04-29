package org.ai.shared.traveller.dialog;

import org.ai.shared.traveller.dialog.STDialogFragment.AbstractDialogFactory;
import org.ai.sharedtraveller.R;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * The class is responsible for the creation of dialogs that ask the user for
 * sending some additional notifications to the clients
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class NotificationDialogFactory extends AbstractDialogFactory
{
	/**
	 * Instantiates a new dialog factory
	 * 
	 * @param inActivity
	 *            the activity to which the new dialog will be associated
	 * @param inTag
	 *            the tag with which the dialog fragment is associated
	 */
	public NotificationDialogFactory(final FragmentActivity inActivity,
			final String inTag)
	{
		super(inActivity, inTag);
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
		final LayoutInflater inflater =
				getActivity().getLayoutInflater();
		return inflater.inflate(R.layout.notification_dialog_content,
				null);
	}
}
