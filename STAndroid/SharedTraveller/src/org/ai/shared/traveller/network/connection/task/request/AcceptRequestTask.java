package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
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
	private static final String SUCCESSFUL_ACCEPTANCE =
			"The request was successfully accepted. New request information "
					+ "is {0}.";

	private static final String UNSUCCESSFUL_ACCEPTANCE =
			"The request was not accepted due to a problem: request code is {0},"
					+ " message {1}.";

	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated. It may not be
	 *            null
	 * @param inRequestImd
	 *            the index of the request to be accepted. It may not be null
	 * @param inRequestInfo
	 *            the container that holds the request to be rejected. It may
	 *            not be null
	 */
	public AcceptRequestTask(final AnnouncementRequestActivity inActivity,
			final int inRequestInd, final IRequestedAnnouncement inRequestInfo)
	{
		super(inActivity, MessageFormat.format(
				"request/info/update/accept/{0}", inRequestInd), inRequestInfo);
	}

	@Override
	protected void onSuccess(final IRequestedAnnouncement inResult)
	{
		Log.d("AcceptRequestTask",
				MessageFormat.format(SUCCESSFUL_ACCEPTANCE, inResult));
		getContext().onRequestAccept(inResult);
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("AcceptRequestTask",
				MessageFormat.format(UNSUCCESSFUL_ACCEPTANCE, inStatusCode,
						inError));
		getContext().onRequestAcceptError();
	}
}
