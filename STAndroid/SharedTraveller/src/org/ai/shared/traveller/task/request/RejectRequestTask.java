package org.ai.shared.traveller.task.request;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

/**
 * The class represents an asynchronous task used to reject a travel request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RejectRequestTask extends ChangeRequestStatusTask
{
	/**
	 * Instantiates a new reject requests task
	 * 
	 * @param inActivity
	 *            the activity to which the current request is associated
	 */
	public RejectRequestTask(final AnnouncementRequestActivity inActivity)
	{
		super(inActivity, "request/reject");
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("RejectRequestTask", "The request was successfully rejected!");
		getActivity().onRequestRejection();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("RejectRequestTask",
				"The request could not be rejected successfully.");
		getActivity().onRequestRejectionError();
	}
}
