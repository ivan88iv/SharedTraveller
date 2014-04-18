package org.ai.shared.traveller.task.request;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class AcceptRequestTask extends AbstractNetworkTask<Void>
{
    public AcceptRequestTask(final Activity inActivity,
            final AbstractRestClient inClient)
    {
        super(inActivity, "request/accept", inClient, Void.class);
    }

    @Override
    protected void onSuccess(final Void inResult)
    {
        Log.d("AcceptRequestTask", "The request was successfully accepted!");
        Toast.makeText(getActivity(),
                "The request has been accepted successfully!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("AcceptRequestTask",
                "The request could not be accepted successfully.");
        Toast.makeText(getActivity(),
                "The request could not be accepted because of a problem.",
                Toast.LENGTH_SHORT).show();
    }

}
