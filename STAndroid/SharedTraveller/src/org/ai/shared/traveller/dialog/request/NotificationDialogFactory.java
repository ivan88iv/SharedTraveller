package org.ai.shared.traveller.dialog.request;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialog.AbstractDialogFactory;
import org.ai.sharedtraveller.R;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

public class NotificationDialogFactory
        extends AbstractDialogFactory
{
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

    @Override
    protected int loadRequestCode()
    {
        return DialogRequestCode.REQUEST_NOTIFICATION.getCode();
    }
}
