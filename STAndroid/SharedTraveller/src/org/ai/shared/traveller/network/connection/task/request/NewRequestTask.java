package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.MainActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

/**
 * The class represents a task used to send new business requests to the server
 * via REST services.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestTask extends AbstractNetworkTask<MainActivity, Void>
{
	private static final String NEW_REQUEST_FAILED =
			"Could not send new request for {0}.";

	/**
	 * Instantiates a new task for creating announcement instances at the server
	 * 
	 * @param inActivity
	 *            the activity this task is connected to
	 * @param inRequestInfo
	 *            the instance holds the request information used to create the
	 *            new travel request in the server side
	 */
	public NewRequestTask(final MainActivity inActivity,
			final IRequestInfo inRequestInfo)
	{
		super(inActivity,
				prepareRestClient(inActivity, "request/new", inRequestInfo),
				Void.class);
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("NewRequestTask", "A new request has been sent successfully!");
		getContext().onSuccessfulNewRequestSending();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("NewRequestTask",
				"The request could not be performed successfully.");
		getContext().onNewRequestSendingProblem();
	}

	/**
	 * The method creates the REST client used to create a new travel request
	 * from the information provided
	 * 
	 * @param inContext
	 *            the request context into which the client is created. It may
	 *            not be null
	 * @param inServerPath
	 *            the path of the service that is called. It may not be null
	 * @param inRequest
	 *            the request information used for the new travel request
	 * @return the new service client used to make the connection to the server
	 */
	private static IServiceClient prepareRestClient(
			final Context inContext, final String inServerPath,
			final IRequestInfo inRequest)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inServerPath, "service path");

		return clientFactory.createNewResourceClient(inContext, inServerPath,
				inRequest,
				MessageFormat.format(NEW_REQUEST_FAILED, inRequest));
	}
}
