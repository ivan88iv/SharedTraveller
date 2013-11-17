package org.ai.shared.traveller.network.connection.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.ai.shared.traveller.exceptions.IllegalUrlException;
import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;

import android.os.AsyncTask;
import android.util.Log;

public abstract class AbstractNetworkTask<Result> extends
        AsyncTask<Void, Void, ServerResponse<Result>> implements INetworkTask
{
    private static final String UNABLE_TO_PARSE_RESULT =
            "Unable to parse result from a service call";

    private static final String UNABLE_TO_CONNECT =
            "Could not connect to service {0}.";

    private final URL requestAddress;

    private final AbstractRestClient restClient;

    private final ServerResponseParser<Result> parser =
            new ServerResponseParser<Result>();

    public AbstractNetworkTask(final String inUrl,
            final AbstractRestClient inClient)
    {
        super();

        try
        {
            requestAddress = new URL(inUrl);
        } catch (final MalformedURLException murle)
        {
            throw new IllegalUrlException(inUrl, murle);
        }

        restClient = inClient;
    }

    protected abstract void onSuccess(final Result inResult);

    protected abstract void onError(final int inStatusCode,
            final String inMessage);

    @Override
    public void perform()
    {
        execute();
    }

    @Override
    public void unbind()
    {
        cancel(true);
    }

    @Override
    protected ServerResponse<Result> doInBackground(final Void... params)
    {
        ServerResponse<Result> response = null;
        try
        {
            response = restClient.callService(requestAddress, parser);

        } catch (final ParseException pe)
        {
            Log.d("AbstractNetwTask", UNABLE_TO_PARSE_RESULT, pe);
            response = new ServerResponse<Result>(null,
                    400, UNABLE_TO_PARSE_RESULT);
        } catch (final ServiceConnectionException sce)
        {
            Log.d("AbstractNetwTask",
                    MessageFormat.format(UNABLE_TO_CONNECT, requestAddress),
                    sce);
            response = new ServerResponse<Result>(null,
                    400,
                    MessageFormat.format(UNABLE_TO_CONNECT, requestAddress));
        }

        return response;
    }

    @Override
    protected void onPostExecute(final ServerResponse<Result> result)
    {
        if (null != result)
        {
            // TODO put actual response code value here
            if (result.getStatusCode() == 200)
            {
                onSuccess(result.getData());
            } else
            {
                onError(result.getStatusCode(), result.getErrorMessage());
            }
        }
    }
}
