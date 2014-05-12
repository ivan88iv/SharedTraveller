package org.ai.shared.traveller.network.connection.task.request;

import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.MainActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

/**
 * The task is used to change the status of a specific request to "declined"
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class DeclineRequestTask extends AbstractNetworkTask<MainActivity, Void>
{
	/**
	 * Instantiates a new request declination task
	 * 
	 * @param inActivity
	 *            the activity from which the task is declined. It may not be
	 *            null
	 * @param inRequestId
	 *            the id of the request which is to be declined. It may not be
	 *            null
	 */
	public DeclineRequestTask(final MainActivity inActivity,
			final Long inRequestId)
	{
		super(inActivity, createServiceClient(inActivity, "request/decline",
				inRequestId), Void.class);
	}

	@Override
	protected void onSuccess(Void inResult)
	{
		Log.d("DeclineRequestTask", "The request was successfully declined!");
		getContext().onSuccessfulRequestDeclination();
	}

	@Override
	protected void onError(int inStatusCode, ErrorResponse inError)
	{
		Log.d("DeclineRequestTask",
				"The request could not be declined successfully.");
		getContext().onRequestDeclinationProblem();
	}

	/**
	 * The method creates and returns the service client used for changing the
	 * status of the specified request in the provided context
	 * 
	 * @param inContext
	 *            the context in which the status of the request is changed. It
	 *            may not be null.
	 * @param inPath
	 *            the service path of the request. It may not be null.
	 * @param inRequestId
	 *            the id of the request to be declined. It may not be null.
	 * @return the created service client
	 */
	private static IServiceClient createServiceClient(
			final Context inContext, final String inPath, final Long inRequestId)
	{
		InstanceAsserter.assertNotNull(inRequestId, "request's id");

		final Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(inRequestId));

		return clientFactory.createFormSubmitionClient(inContext, inPath,
				params);
	}
}
