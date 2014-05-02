package org.ai.shared.traveller.network.connection.task.request;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

/**
 * The class represents an asynchronous task used to reject a travel request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RejectRequestTask
		extends ChangeRequestStatusTask<AnnouncementRequestActivity>
{
	/**
	 * Instantiates a new reject requests task
	 * 
	 * @param inActivity
	 *            the activity to which the current request is associated. It
	 *            may not be null
	 * @param inRequestId
	 *            the id of the request to be rejected. It may not be null
	 */
	public RejectRequestTask(final AnnouncementRequestActivity inActivity,
			final Long inRequestId)
	{
		super(inActivity, "request/reject", inRequestId);
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("RejectRequestTask", "The request was successfully rejected!");
		getContext().onRequestRejection();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("RejectRequestTask",
				"The request could not be rejected successfully.");
		getContext().onRequestRejectionError();
	}
}
