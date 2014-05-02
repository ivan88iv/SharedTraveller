package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.ai.shared.traveller.request.RequestsAdapter;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The class represents a task that is used in order to extract requests for a
 * specified announcement from the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestsExtractionTask
		extends AbstractNetworkTask<AnnouncementRequestActivity,
		List<? extends IRequestInfo>>
{
	private static final String SUCCESSFUL_EXTRACTION =
			"Requests for the announcement {0}  were extracted successfully. "
					+ "Requests: {1}.";

	private static final String UNSUCCESSFUL_EXTRACTION =
			"No requests were extracted because of a problem. "
					+ "Response status: {0}\n Error message: {1}.";

	private final IAnnouncement announcement;

	private final RequestsAdapter adapter;

	/**
	 * The constructor instantiates a new requests extraction task
	 * 
	 * @param inActivity
	 *            the activity which calls the task
	 * @param inAnnouncement
	 *            the announcement for which requests are extracted. It may not
	 *            be null.
	 * @param inAdapter
	 *            the request adapter instance to which extracted requests are
	 *            attached. It may not be null.
	 */
	public RequestsExtractionTask(
			final AnnouncementRequestActivity inActivity,
			final IAnnouncement inAnnouncement,
			final RequestsAdapter inAdapter)
	{
		super(inActivity, prepareServiceClient(inActivity, inAnnouncement),
				new TypeReference<List<? extends IRequestInfo>>()
				{
				});

		InstanceAsserter.assertNotNull(inAnnouncement, "announcement");
		InstanceAsserter.assertNotNull(inAdapter, "adapter");

		announcement = inAnnouncement;
		adapter = inAdapter;
	}

	@Override
	protected void onSuccess(final List<? extends IRequestInfo> inResult)
	{
		Log.d("RequestsExtractionTask", MessageFormat.format(
				SUCCESSFUL_EXTRACTION, announcement, inResult));
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
	 * The method constructs the server path used to make the server call
	 * 
	 * @param inAnnouncement
	 *            the announcement for which requests are extracted
	 * @return the constructed server path
	 */
	private static String constructServerPath(
			final IAnnouncement inAnnouncement)
	{
		String serverPath = "request/announcement/all/?from=";
		serverPath += inAnnouncement.getFrom();
		serverPath += ("&to=" + inAnnouncement.getTo());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		serverPath += ("&departureDate=" +
				sdf.format(inAnnouncement.getDepartureDate()));
		serverPath += ("&driver=" + inAnnouncement.getDriverUsername());

		return serverPath;
	}

	/**
	 * The method prepares the service client used for the call
	 * 
	 * @param inContext
	 *            the context used to prepare the client
	 * @param inAnnouncement
	 *            the announcement used to prepare the client
	 * @return the prepared service client
	 */
	private static IServiceClient prepareServiceClient(final Context inContext,
			final IAnnouncement inAnnouncement)
	{
		final IServiceClientFactory clientFactory =
				DomainManager.getInstance().getServiceClientFactory();
		return clientFactory.createSimpleClient(inContext,
				constructServerPath(inAnnouncement));
	}
}