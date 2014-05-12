package org.ai.shared.traveller.dialog;

import org.ai.sharedtraveller.R;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * The class is responsible for representing dialogs throughout the application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class STDialogFragment extends SimpleDialogFragment
{
	/**
	 * The class represents an application dialog's contextual information
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static abstract class AbstractDialogContext
	{
		private final FragmentActivity activity;

		private final String tag;

		private STDialogFragment attachedDialog;

		/**
		 * Instantiates a new dialog context
		 * 
		 * @param inActivity
		 *            the activity in which the new dialog is to be created
		 * @param inTag
		 *            the tag with which the dialog fragment is to be related
		 */
		protected AbstractDialogContext(
				final FragmentActivity inActivity,
				final String inTag)
		{
			activity = inActivity;
			tag = inTag;
		}

		/**
		 * The method loads and returns the id of the resource used for the
		 * title of the dialog
		 * 
		 * @return the id of the resource used for the title of the dialog
		 */
		protected abstract int loadTitleResourceId();

		/**
		 * The method loads and returns the message used for the dialog's
		 * description
		 * 
		 * @return the message used for the dialog's description
		 */
		protected abstract String loadDescriptionMsg();

		/**
		 * The method loads and returns the id of the resource used for the
		 * dialog's confirmation button
		 * 
		 * @return the id of the resource used for the dialog's confirmation
		 *         button
		 */
		protected abstract int loadConfirmBtnResourceId();

		/**
		 * The method loads and returns the id of the dialog's rejection button
		 * 
		 * @return the id of the dialog's rejection button
		 */
		protected abstract int loadRejectBtnResourceId();

		/**
		 * The method loads and returns the content of the dialog
		 * 
		 * @return the content of the dialog
		 */
		protected abstract View loadContent();

		/**
		 * The method loads and returns the code of the request specified by the
		 * current dialog
		 * 
		 * @return the code of the request specified by the current dialog
		 */
		protected abstract int loadRequestCode();

		/**
		 * The method returns the activity which is related to the newly created
		 * dialog
		 * 
		 * @return the activity which is related to the newly created dialog
		 */
		protected FragmentActivity getActivity()
		{
			return activity;
		}

		/**
		 * The method returns the tag with which the new dialog is to be created
		 * 
		 * @return the tag with which the new dialog is to be created
		 */
		protected String getTag()
		{
			return tag;
		}

		/**
		 * The method attaches a dialog to the context
		 * 
		 * @param inDialog
		 *            the dialog to be attached. It may not be null
		 */
		private void attachDialog(final STDialogFragment inDialog)
		{
			InstanceAsserter.assertNotNull(inDialog, "dialog");

			attachedDialog = inDialog;
			inDialog.confirmBtnResourceId = loadConfirmBtnResourceId();
			inDialog.rejectBtnResourceId = loadRejectBtnResourceId();
			inDialog.requestCode = loadRequestCode();
			inDialog.dialogView = prepareDialogView();
		}

		/**
		 * Returns the attached dialog to this context if any or null
		 * 
		 * @return the attached dialog to this context if any or null
		 */
		private STDialogFragment getAttachedDialog()
		{
			return attachedDialog;
		}

		private View prepareDialogView()
		{
			final View dialogView = LayoutInflater.from(
					activity).inflate(R.layout.requests_approval_dialog,
					null);
			final TextView title = (TextView)
					dialogView.findViewById(R.id.dialog_title);
			title.setText(activity.getResources()
					.getString(loadTitleResourceId()));

			final TextView description = (TextView)
					dialogView.findViewById(R.id.dialog_description);
			description.setText(loadDescriptionMsg());

			final View content = loadContent();

			if (content != null)
			{
				final FrameLayout contentHolder = (FrameLayout)
						dialogView.findViewById(R.id.dialog_content);
				contentHolder.addView(content);
			}

			return dialogView;
		}
	}

	private int confirmBtnResourceId;

	private int rejectBtnResourceId;

	private int requestCode;

	private View dialogView;

	/**
	 * This constructor should not be used by application code. Its sole purpose
	 * is to allow the android framework to restore the fragment's state
	 * correctly. Instead the method {@link #show(AbstractDialogContext)} should
	 * be utilized to show the current dialog
	 */
	public STDialogFragment()
	{
	}

	/**
	 * The method creates and displays a new dialog with the help of the
	 * specified dialog context
	 * 
	 * @param inContext
	 *            the dialog's context used in the new dialog's instantiation.
	 *            It may not be null
	 */
	public static void show(final AbstractDialogContext inContext)
	{
		InstanceAsserter.assertNotNull(inContext, "dialog context");

		STDialogFragment dialog = inContext.getAttachedDialog();
		if (null == dialog)
		{
			dialog = new STDialogFragment();
			inContext.attachDialog(dialog);
		}

		dialog.show(inContext.getActivity().getSupportFragmentManager(),
				inContext.getTag());
	}

	@Override
	protected Builder build(final Builder initialBuilder)
	{
		final Resources resources = getActivity().getResources();

		initialBuilder.setView(dialogView);
		initialBuilder.setPositiveButton(
				resources.getString(confirmBtnResourceId),
				new View.OnClickListener()
				{
					@Override
					public void onClick(final View v)
					{
						final ISimpleDialogListener listener = getDialogListener();
						if (listener != null)
						{
							listener.onPositiveButtonClicked(requestCode);
						}

						dismiss();
					}
				});
		initialBuilder.setNegativeButton(resources.
				getString(rejectBtnResourceId),
				new View.OnClickListener()
				{
					@Override
					public void onClick(final View v)
					{
						final ISimpleDialogListener listener = getDialogListener();
						if (listener != null)
						{
							listener.onNegativeButtonClicked(requestCode);
						}

						dismiss();
					}
				});

		return initialBuilder;
	}
}
