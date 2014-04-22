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
import org.shared.traveller.utility.InstanceAsserter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The class represents the common functionality used for all tasks that perform
 * REST server calls
 * 
 * @author "Ivan Ivanov"
 * 
 * @param <A>
 *            the concrete type of the activity related to this task
 * @param <Result>
 *            the type of the body contained in the server response that is
 *            received after making the call to the server
 */
public abstract class AbstractNetworkTask<A extends Activity, Result> extends
		AsyncTask<Void, Void, ServerResponse<Result>> implements INetworkTask{;
	private static final String UNABLE_TO_PARSE_RESULT =
			"Unable to parse result from a service call";

	private static final String UNABLE_TO_CONNECT =
			"Could not connect to service {0}.";

	private final URL requestAddress;

	private final AbstractRestClient restClient;

	private final ServerResponseParser<Result> parser;

	private final PathResolver resolver;

	private A activity;
	/**
	 * The constructor creates a new abstract network task
	 * 
	 * @param inActivity
	 *            the activity for which the task is executed. It may not be
	 *            null
	 * @param inServerPath
	 *            the path in the server. It may not be null
	 * @param inClient
	 *            the client used to make the REST service calls. It may not be
	 *            null.
	 * @param inClass
	 *            the class of the server response body type. It may not be null
	 */
	public AbstractNetworkTask(final A inActivity,
			final String inServerPath, final AbstractRestClient inClient,
			final Class<Result> inClass)
	{
		super();
		InstanceAsserter.assertNotNull(inActivity, "activity");
		InstanceAsserter.assertNotNull(inServerPath, "server path");
		InstanceAsserter.assertNotNull(inClient, "client");
		InstanceAsserter.assertNotNull(inClass, "class instance");
		activity = inActivity;
		resolver = new PathResolver(inActivity);
		parser = new ServerResponseParser<Result>(inClass);
		requestAddress = createRequestURL(inServerPath);
		restClient = inClient;
	}
	/**
	 * The constructor creates a new abstract network task
	 * 
	 * @param inActivity
	 *            the activity for which the task is executed. It may not be
	 *            null
	 * @param inServerPath
	 *            the path in the server. It may not be null
	 * @param inClient
	 *            the client used to make the REST service calls. It may not be
	 *            null
	 * @param inRef
	 *            the reference of the server response body type. It may not be
	 *            null
	 */
	public AbstractNetworkTask(final A inActivity,
			final String inServerPath, final AbstractRestClient inClient,
			final TypeReference<Result> inRef)
	{
		super();
		InstanceAsserter.assertNotNull(inActivity, "activity");
		InstanceAsserter.assertNotNull(inServerPath, "server path");
		InstanceAsserter.assertNotNull(inClient, "client");
		InstanceAsserter.assertNotNull(inRef,
				"reference of the response body type");
		activity = inActivity;
		resolver = new PathResolver(inActivity);
		parser = new ServerResponseParser<Result>(inRef);
		requestAddress = createRequestURL(inServerPath);
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
		activity = null;
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
			Log.d("AbstractNetworkTask",
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
			if (result.getStatusCode() < 400)
			{
				onSuccess(result.getResponseBody());
			} else
			{
				onError(result.getStatusCode(), result.getErrorResponse());
			}
		}
	}
	/**
	 * Returns the activity associated with the network task
	 * 
	 * @return the activity associated with the network task
	 */
	protected A getActivity()
	{
		return activity;
	}
	/**
	 * Creates the request URL that corresponds to the given server path
	 * 
	 * @param inServerPath
	 *            the server path that determines the request URL
	 * @return the formed request URL
	 */
	private URL createRequestURL(final String inServerPath)
	{
		URL requestUrl = null;
		try
		{
			requestUrl = new URL(resolver.resolvePath(inServerPath));
		} catch (final MalformedURLException murle)
		{
			throw new IllegalUrlException(inServerPath, murle);
		}
		return requestUrl;
	}}
