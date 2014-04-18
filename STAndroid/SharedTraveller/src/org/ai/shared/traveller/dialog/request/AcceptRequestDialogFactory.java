package org.ai.shared.traveller.dialog.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialog.AbstractDialogFactory;
import org.ai.sharedtraveller.R;

import android.support.v4.app.FragmentActivity;
import android.view.View;

public class AcceptRequestDialogFactory
        extends AbstractDialogFactory
{
    private final String sender;

    public AcceptRequestDialogFactory(
            final FragmentActivity inActivity,
            final String inSender)
    {
        super(inActivity, "accept_request");

        sender = inSender;
    }

    @Override
    protected int loadTitleResourceId()
    {
        return R.string.requests_accept_title;
    }

    @Override
    protected String loadDescriptionMsg()
    {
        final String description = getActivity().getResources().
                getString(R.string.requests_accept_description);
        return MessageFormat.format(description,
                sender);
    }

    @Override
    protected int loadConfirmBtnResourceId()
    {
        return R.string.requests_confirm_btn;
    }

    @Override
    protected int loadRejectBtnResourceId()
    {
        return R.string.requests_reject_btn;
    }

    @Override
    protected View loadContent()
    {
        return null;
    }

    @Override
    protected int loadRequestCode()
    {
        return DialogRequestCode.ACCEPT_REQUEST_CODE.getCode();
    }
}
