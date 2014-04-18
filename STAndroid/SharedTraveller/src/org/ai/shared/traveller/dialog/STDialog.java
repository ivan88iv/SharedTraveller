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

public class STDialog extends SimpleDialogFragment
{
    public static abstract class AbstractDialogFactory
    {
        private final FragmentActivity activity;

        private final String tag;

        protected AbstractDialogFactory(
                final FragmentActivity inActivity,
                final String inTag)
        {
            activity = inActivity;
            tag = inTag;
        }

        protected abstract int loadTitleResourceId();

        protected abstract String loadDescriptionMsg();

        protected abstract int loadConfirmBtnResourceId();

        protected abstract int loadRejectBtnResourceId();

        protected abstract View loadContent();

        protected abstract int loadRequestCode();

        public STDialog create()
        {
            final STDialog dialog = new STDialog();
            dialog.titleResourceId = loadTitleResourceId();
            dialog.descriptionMsg = loadDescriptionMsg();
            dialog.confirmBtnResourceId = loadConfirmBtnResourceId();
            dialog.rejectBtnResourceId = loadRejectBtnResourceId();
            dialog.content = loadContent();
            dialog.requestCode = loadRequestCode();

            return dialog;
        }

        public FragmentActivity getActivity()
        {
            return activity;
        }

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

    public static void show(final AbstractDialogFactory inFactory)
    {
        final STDialog dialog = inFactory.create();
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
                            listener.onPositiveButtonClicked(requestCode);
                        }

                        dismiss();
                    }
                });

        return initialBuilder;
    }

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
