package org.ai.shared.traveller.network.connection.task.notification;

import java.text.MessageFormat;
import java.util.List;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.notification.NotificationService;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The task is used to obtain the new notifications for the specified user from
 * the server side
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationsExtractionTask extends
		AbstractNetworkTask<NotificationService, List<? extends INotification>>
{
	private static final String EXTRACTION_FAILUERE =
			"No notifications could be extracted because of a problem: "
					+ "response code: {0}, error message: {1}";

	private static final String EXTRACTION_SUCCESS =
			"The notifications {0} were successfully extracted.";

	/**
	 * Creates a new task for notification extraction
	 * 
	 * @param inService
	 *            the service responsible for extracting the notifications. It
	 *            may not be null.
	 * @param inReceiverId
	 *            the id of the user for who notifications are extracted. It may
	 *            not be null
	 */
	public NotificationsExtractionTask(final NotificationService inService,
			final Long inReceiverId)
	{
		super(inService, constructClient(inService, inReceiverId),
				new TypeReference<List<? extends INotification>>()
				{
				});
	}

	@Override
	protected void onSuccess(List<? extends INotification> inResult)
	{
		Log.d("NotificationsExtractionTask", MessageFormat.format(
				EXTRACTION_SUCCESS, inResult));
		getContext().onSuccessfulExtraction(inResult);
	}

	@Override
	protected void onError(int inStatusCode, ErrorResponse inError)
	{
		Log.e("NotificationsExtractionTask", MessageFormat.format(
				EXTRACTION_FAILUERE, inStatusCode, inError));
	}

	/**
	 * The method constructs the client used by this task to make the
	 * notifications call
	 * 
	 * @param inContext
	 *            the context used by the client. It may not be null
	 * @param inReceiverId
	 *            the id of the user that shall receive the notifications. It
	 *            may not be null
	 * @return the newly constructed service client
	 */
	private static IServiceClient constructClient(final Context inContext,
			final Long inReceiverId)
	{
		// TODO specify the auth token
		return clientFactory.createFormSubmitionClient(inContext,
				"notification/all/" + inReceiverId, null);
	}
}
