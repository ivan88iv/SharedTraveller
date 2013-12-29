package org.ai.shared.traveller.task.announcement;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class NewAnnouncementTask extends AbstractNetworkTask<Void>
{
    private final Context context;

    public NewAnnouncementTask(final AbstractRestClient inClient,
            final Context inContext)
    {
        super("stserver/announcement/new", inClient, Void.class);

        context = inContext;
    }

    @Override
    protected void onSuccess(final Void inResult)
    {
        Log.d("NewAnnouncementTask", "Successful creation of an announcement");
        Toast.makeText(context, "Successful creation of an announcement",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("NewAnnouncementTask", "Unsuccessful creation of an announcement");
        Toast.makeText(context, "Unsuccessful creation of an announcement",
                Toast.LENGTH_SHORT).show();
    }
}
