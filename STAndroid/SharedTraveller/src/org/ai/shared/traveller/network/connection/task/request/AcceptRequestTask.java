package org.ai.shared.traveller.network.connection.task.request;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

/**
 * The class represents the asynchronous task used for accepting a request
 * instance
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class AcceptRequestTask
		extends ChangeRequestStatusTask<AnnouncementRequestActivity>
{
	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated. It may not be
	 *            null
	 * @param inRequestId
	 *            the id of the request to be accepted. It may not be null
	 */
	public AcceptRequestTask(final AnnouncementRequestActivity inActivity,
			final Long inRequestId)
	{
		super(inActivity, "request/accept", inRequestId);
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("AcceptRequestTask", "The request was successfully accepted!");
		getContext().onRequestAccept();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("AcceptRequestTask",
				"The request could not be accepted successfully.");
		getContext().onRequestAcceptError();
	}
}
