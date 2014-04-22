package org.ai.shared.traveller.task.request;

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
public class AcceptRequestTask extends ChangeRequestStatusTask
{
	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated
	 */
	public AcceptRequestTask(final AnnouncementRequestActivity inActivity)
	{
		super(inActivity, "request/accept");
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("AcceptRequestTask", "The request was successfully accepted!");
		getActivity().onRequestAccept();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("AcceptRequestTask",
				"The request could not be accepted successfully.");
		getActivity().onRequestAcceptError();
	}
}
