package org.ai.shared.traveller.dialog.request;

import org.ai.sharedtraveller.R;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * The class represents the dialog used for sending new travel requests for a
 * certain announcement
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestDialog extends SimpleDialogFragment
{
    /**
     * The method displays the new request dialog
     * 
     * @param activity
     *            the activity that shows the dialog
     */
    public static void show(final FragmentActivity activity)
    {
        final NewRequestDialog dialog = new NewRequestDialog();
        dialog.show(activity.getSupportFragmentManager(), "new_request");
    }

    @Override
    protected Builder build(final Builder initialBuilder)
    {
        final Resources resources = getActivity().getResources();
        final View dialogView =
                LayoutInflater.from(getActivity()).inflate(
                        R.layout.new_request_dialog, null);
        initialBuilder.setView(dialogView);

        initialBuilder.setPositiveButton(resources.
                getString(R.string.new_request_confirmation_btn),
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(final View v)
                    {
                        final ISimpleDialogListener listener = getDialogListener();
                        if (listener != null)
                        {
                            listener.onPositiveButtonClicked(0);
                        }

                        dismiss();
                    }
                });
        initialBuilder.setNegativeButton(resources.
                getString(R.string.new_request_rejection_btn),
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(final View v)
                    {
                        dismiss();
                    }
                });

        return initialBuilder;
    }
}
