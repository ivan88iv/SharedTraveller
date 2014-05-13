package org.ai.shared.traveller.dialog.request;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialogFragment.AbstractDialogContext;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.utility.InstanceAsserter;

import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * The class represents the dialog's context used for sending new travel
 * requests for a certain announcement
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestDialogContext extends AbstractDialogContext
{
	private final IAnnouncement announcement;

	/**
	 * Creates a new request dialog context
	 * 
	 * @param inActivity
	 *            the activity in which the dialog is shown. It may not be null
	 * @param inAnnouncement
	 *            the announcement for which the dialog is shown. It may not be
	 *            null
	 */
	public NewRequestDialogContext(final FragmentActivity inActivity,
			final IAnnouncement inAnnouncement)
	{
		super(inActivity, "new_request");

		InstanceAsserter.assertNotNull(inAnnouncement, "announcement");

		announcement = inAnnouncement;
	}

	@Override
	protected int loadTitleResourceId()
	{
		return R.string.new_request_title;
	}

	@Override
	protected String loadDescriptionMsg()
	{
		final String description = getActivity().getResources().
				getString(R.string.new_request_sub_title);
		final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());

		return MessageFormat.format(description,
				announcement.getDriverUsername(),
				announcement.getFrom(), announcement.getTo(),
				formatter.format(announcement.getDepartureDate()));
	}

	@Override
	protected int loadConfirmBtnResourceId()
	{
		return R.string.new_request_confirmation_btn;
	}

	@Override
	protected int loadRejectBtnResourceId()
	{
		return R.string.new_request_rejection_btn;
	}

	@Override
	protected View loadContent()
	{
		return null;
	}

	@Override
	protected int loadRequestCode()
	{
		return DialogRequestCode.NEW_REQUEST.getCode();
	}
}
