package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
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
	private static final String SUCCESSFUL_REJECTION =
			"The request was successfully rejected. The new request"
					+ " information is {0}";

	private static final String UNSUCCESSFUL_REJECTION =
			"The request could not be rejected because of a problem: "
					+ "response code {0}, message {1}.";

	/**
	 * Instantiates a new reject requests task
	 * 
	 * @param inActivity
	 *            the activity to which the current request is associated. It
	 *            may not be null
	 * @param inRequestInd
	 *            the index of the request to be rejected. It may not be null
	 * @param inRequestInfo
	 *            the instance that holds the request to be rejected. It may not
	 *            be null
	 */
	public RejectRequestTask(final AnnouncementRequestActivity inActivity,
			final int inRequestInd, final IRequestedAnnouncement inRequestInfo)
	{
		super(inActivity, MessageFormat.format(
				"request/info/update/reject/{0}", inRequestInd), inRequestInfo);
	}

	@Override
	protected void onSuccess(final IRequestedAnnouncement inResult)
	{
		Log.d("RejectRequestTask",
				MessageFormat.format(SUCCESSFUL_REJECTION, inResult));
		getContext().onRequestRejection(inResult);
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("RejectRequestTask",
				MessageFormat.format(UNSUCCESSFUL_REJECTION, inStatusCode,
						inError));
		getContext().onRequestRejectionError();
	}
}
