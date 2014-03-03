package org.ai.shared.traveller.task.announcement;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class NewAnnouncementTask extends AbstractNetworkTask<Void>
{
    public NewAnnouncementTask(final Activity inActivity,
            final AbstractRestClient inClient)
    {
        super(inActivity, "stserver/announcement/new", inClient, Void.class);
    }

    @Override
    protected void onSuccess(final Void inResult)
    {
        Log.d("NewAnnouncementTask", "Successful creation of an announcement");
        Toast.makeText(getActivity(), "Successful creation of an announcement",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("NewAnnouncementTask", "Unsuccessful creation of an announcement");
        Toast.makeText(getActivity(),
                "Unsuccessful creation of an announcement",
                Toast.LENGTH_SHORT).show();
    }
}
