package org.ai.shared.traveller.task.request;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * The class represents a task used to send new business requests to the server
 * via REST services.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestTask extends AbstractNetworkTask<Void>
{
    /**
     * Instantiates a new task for creating announcement instances at the server
     * 
     * @param inActivity
     *            the activity this task is connected to
     * @param inClient
     *            the REST client used to send the REST request to the server
     */
    public NewRequestTask(final Activity inActivity,
            final AbstractRestClient inClient)
    {
        super(inActivity, "request/new", inClient, Void.class);
    }

    @Override
    protected void onSuccess(final Void inResult)
    {
        Log.d("NewRequestTask", "A new request has been sent successfully!");
        Toast.makeText(getActivity(),
                "A new request has been sent successfully!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("NewRequestTask",
                "The request could not be performed successfully.");
        Toast.makeText(getActivity(),
                "A request could not be sent because of a problem.",
                Toast.LENGTH_SHORT).show();
    }
}
