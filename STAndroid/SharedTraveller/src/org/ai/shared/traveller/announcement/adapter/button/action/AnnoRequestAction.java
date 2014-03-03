package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.client.domain.rest.Announcement;

import android.content.Context;
import android.widget.Toast;

public class AnnoRequestAction implements IButtonAction
{

    @Override
    public void perform(final Context cxt, final Announcement announcement)
    {
        final Toast toast = Toast.makeText(cxt, "Send request",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
