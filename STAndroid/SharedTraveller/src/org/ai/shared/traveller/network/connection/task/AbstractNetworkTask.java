package org.ai.shared.traveller.network.connection.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.ai.shared.traveller.exceptions.IllegalUrlException;
import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.path.resolver.PathResolver;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.os.AsyncTask;
import android.util.Log;

/**
 * The class represents the common functionality used for all tasks that perform
 * REST server calls
 * 
 * @author Ivan
 * 
 * @param <Result>
 *            the type of the body contained in the server response that is
 *            received after making the call to the server
 */
public abstract class AbstractNetworkTask<Result> extends
        AsyncTask<Void, Void, ServerResponse<Result>> implements INetworkTask
{
    private static final String UNABLE_TO_PARSE_RESULT =
            "Unable to parse result from a service call";

    private static final String UNABLE_TO_CONNECT =
            "Could not connect to service {0}.";

    private final URL requestAddress;

    private final AbstractRestClient restClient;

    private final ServerResponseParser<Result> parser;

    private final PathResolver resolver = new PathResolver();

    /**
     * The constructor creates a new abstract network task
     * 
     * @param inServerPath
     *            the path in the server
     * @param inClient
     *            the client used to make the REST service calls
     * @param inClass
     *            the class of the server response body type
     */
    public AbstractNetworkTask(final String inServerPath,
            final AbstractRestClient inClient, final Class<Result> inClass)
    {
        super();

        try
        {
            parser = new ServerResponseParser<Result>(inClass);
            requestAddress = new URL(resolver.resolvePath(inServerPath));
        } catch (final MalformedURLException murle)
        {
            throw new IllegalUrlException(inServerPath, murle);
        }

        restClient = inClient;
    }

    /**
     * The method is executed on successful execution of the service request
     * 
     * @param inResult
     *            body of the server response received after making the service
     *            call
     */
    protected abstract void onSuccess(final Result inResult);

    /**
     * The method is executed on unsuccessful execution of the service call
     * 
     * @param inStatusCode
     *            the status code of the server response
     * @param inError
     *            the error container of the server response
     */
    protected abstract void onError(final int inStatusCode,
            final ErrorResponse inError);

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
            final ErrorResponse content = new ErrorResponse();
            content.setMessage(UNABLE_TO_PARSE_RESULT);
            response = new ServerResponse<Result>(400, content);
        } catch (final ServiceConnectionException sce)
        {
            Log.d("AbstractNetwTask",
                    MessageFormat.format(UNABLE_TO_CONNECT, requestAddress),
                    sce);
            final ErrorResponse content = new ErrorResponse();
            content.setMessage(MessageFormat.format(
                    UNABLE_TO_CONNECT, requestAddress));
            response = new ServerResponse<Result>(400, content);
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
                onSuccess(result.getResponseBody());
            } else
            {
                onError(result.getStatusCode(), result.getErrorResponse());
            }
        }
    }
}
