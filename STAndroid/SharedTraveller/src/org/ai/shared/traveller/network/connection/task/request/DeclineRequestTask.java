package org.ai.shared.traveller.network.connection.task.request;

import org.ai.shared.traveller.MainActivity;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

/**
 * The task is used to change the status of a specific request to "declined"
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class DeclineRequestTask extends ChangeRequestStatusTask<MainActivity>
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
		super(inActivity, "request/decline", inRequestId);
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
}
