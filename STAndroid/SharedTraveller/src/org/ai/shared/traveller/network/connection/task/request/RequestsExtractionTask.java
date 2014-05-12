package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.ai.shared.traveller.request.RequestsAdapter;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

/**
 * The class represents a task that is used in order to extract requests for a
 * specified announcement from the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestsExtractionTask
		extends AbstractNetworkTask<AnnouncementRequestActivity,
		IRequestedAnnouncement>
{
	private static final String SUCCESSFUL_EXTRACTION =
			"Requests for the announcement's id {0} "
					+ "were extracted successfully. Requests: {1}.";

	private static final String UNSUCCESSFUL_EXTRACTION =
			"No requests were extracted because of a problem. "
					+ "Response status: {0}\n Error message: {1}.";

	private final Long announcementId;

	private final RequestsAdapter adapter;

	/**
	 * The constructor instantiates a new requests extraction task
	 * 
	 * @param inActivity
	 *            the activity which calls the task
	 * @param inAnnouncementId
	 *            the id of the announcement for which requests are extracted.
	 *            It may not be null.
	 * @param inAdapter
	 *            the request adapter instance to which extracted requests are
	 *            attached. It may not be null.
	 */
	public RequestsExtractionTask(
			final AnnouncementRequestActivity inActivity,
			final Long inAnnouncementId, final RequestsAdapter inAdapter)
	{
		super(inActivity, prepareServiceClient(inActivity, inAnnouncementId),
				IRequestedAnnouncement.class);

		announcementId = inAnnouncementId;
		adapter = inAdapter;
	}

	@Override
	protected void onSuccess(final IRequestedAnnouncement inResult)
	{
		Log.d("RequestsExtractionTask", MessageFormat.format(
				SUCCESSFUL_EXTRACTION, announcementId, inResult));
		getContext().onRequestsExtractionSuccess(inResult, adapter);
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("RequestsExtractionTask", MessageFormat.format(
				UNSUCCESSFUL_EXTRACTION, inStatusCode, inError));
		getContext().onRequestsExtractionError();
	}

	/**
	 * The method prepares the service client used for the call
	 * 
	 * @param inContext
	 *            the context used to prepare the client. It may not be null
	 * @param inAnnouncementId
	 *            the id of the announcement for which requests are extracted.
	 *            It may not be null
	 * @return the prepared service client
	 */
	private static IServiceClient prepareServiceClient(final Context inContext,
			final Long inAnnouncementId)
	{
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement's id");

		return clientFactory.createResourceSubmittionClient(inContext,
				MessageFormat.format("request/info/for/announcement/{0}",
						inAnnouncementId), null, null);
	}
}
