package org.ai.shared.traveller.dialog;

import org.ai.sharedtraveller.R;

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
	 * The class represents a factory that is used to provide the common
	 * functionality for creating an application dialog
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static abstract class AbstractDialogFactory
	{
		private final FragmentActivity activity;

		private final String tag;

		/**
		 * Instantiates a new dialog factory
		 * 
		 * @param inActivity
		 *            the activity in which the new dialog is to be created
		 * @param inTag
		 *            the tag with which the dialog fragment is to be related
		 */
		protected AbstractDialogFactory(
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
		 * The factory method used for creating a new dialog
		 * 
		 * @return the newly created dialog
		 */
		public STDialogFragment create()
		{
			final STDialogFragment dialog = new STDialogFragment();
			dialog.titleResourceId = loadTitleResourceId();
			dialog.descriptionMsg = loadDescriptionMsg();
			dialog.confirmBtnResourceId = loadConfirmBtnResourceId();
			dialog.rejectBtnResourceId = loadRejectBtnResourceId();
			dialog.content = loadContent();
			dialog.requestCode = loadRequestCode();

			return dialog;
		}

		/**
		 * The method returns the activity which is related to the newly created
		 * dialog
		 * 
		 * @return the activity which is related to the newly created dialog
		 */
		public FragmentActivity getActivity()
		{
			return activity;
		}

		/**
		 * The method returns the tag with which the new dialog is to be created
		 * 
		 * @return the tag with which the new dialog is to be created
		 */
		public String getTag()
		{
			return tag;
		}
	}

	private int titleResourceId;

	private String descriptionMsg;

	private int confirmBtnResourceId;

	private int rejectBtnResourceId;

	private View content;

	private int requestCode;

	/**
	 * The method shows creates and displays a new dialog with the help of the
	 * specified dialog factory
	 * 
	 * @param inFactory
	 *            the dialog factory used for the new dialog instantiation
	 */
	public static void show(final AbstractDialogFactory inFactory)
	{
		final STDialogFragment dialog = inFactory.create();
		dialog.show(
				inFactory.getActivity().getSupportFragmentManager(),
				inFactory.getTag());
	}

	@Override
	protected Builder build(final Builder initialBuilder)
	{
		final FragmentActivity activity = getActivity();
		final Resources resources = getActivity().getResources();
		configureView(activity, resources, initialBuilder);

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

	/**
	 * The method configures and orders the view of the current dialog
	 * 
	 * @param inActivity
	 *            the activity with which the dialog fragment is associated
	 * @param inResources
	 *            the resources information of the current dialog
	 * @param initialBuilder
	 *            the dialog builder instance used to create the dialog
	 */
	private void configureView(final FragmentActivity inActivity,
			final Resources inResources,
			final Builder initialBuilder)
	{
		final View dialogView = LayoutInflater.from(
				inActivity).inflate(R.layout.requests_approval_dialog,
				null);
		final TextView title = (TextView)
				dialogView.findViewById(R.id.dialog_title);
		title.setText(inResources.getString(titleResourceId));

		final TextView description = (TextView)
				dialogView.findViewById(R.id.dialog_description);
		description.setText(descriptionMsg);

		if (content != null)
		{
			final FrameLayout contentHolder = (FrameLayout)
					dialogView.findViewById(R.id.dialog_content);
			contentHolder.addView(content);
		}

		initialBuilder.setView(dialogView);
	}
}
