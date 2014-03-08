package org.ai.shared.traveller.task.announcement;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * The class represents a task used for sending REST requests for new
 * announcement creation to the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewAnnouncementTask extends AbstractNetworkTask<Void>
{
    /**
     * Instantiates a new task for creating announcement instances at the server
     * 
     * @param inActivity
     *            the activity this task is connected to
     * @param inClient
     *            the REST client used to send the REST request to the server
     */
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
