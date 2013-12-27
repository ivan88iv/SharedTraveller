package org.ai.shared.traveller.announcement.input;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

public class NewAnnouncementTask extends AbstractNetworkTask<Void>
{

    public NewAnnouncementTask(final AbstractRestClient inClient)
    {
        super("stserver/announcement/new", inClient, Void.class);
    }

    @Override
    protected void onSuccess(final Void inResult)
    {
        Log.d("NewAnnouncementTask", "Successful creation of an announcement");
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("NewAnnouncementTask", "Unsuccessful creation of an announcement");
    }
}
