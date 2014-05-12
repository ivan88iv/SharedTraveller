package org.ai.shared.traveller.network.connection.task.request;

import java.text.MessageFormat;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.rest.RequestedAnnouncement;
import org.shared.traveller.utility.InstanceAsserter;

import android.app.Activity;
import android.content.Context;

/**
 * The class represents the common functionality for tasks that change the
 * status of a selected request
 * 
 * @param <T>
 *            the concrete activity type into which the task is used
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class ChangeRequestStatusTask<T extends Activity>
		extends AbstractNetworkTask<T, IRequestedAnnouncement>
{
	private static final String REQUEST_INFO_SUBMITION_ERROR =
			"The request information {0} could not be submitted to the server.";

	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated. It may not be
	 *            null.
	 * @param inPath
	 *            the path of the service being called. It may not be null.
	 * @param inRequestInfo
	 *            the request information to be updated. It may not be null
	 */
	public ChangeRequestStatusTask(final T inActivity,
			final String inPath, final IRequestedAnnouncement inRequestInfo)
	{
		super(inActivity,
				createServiceClient(inActivity, inPath, inRequestInfo),
				IRequestedAnnouncement.class);
	}

	/**
	 * The method creates and returns the service client used for changing the
	 * status of the specified request in the provided context
	 * 
	 * @param inContext
	 *            the context in which the status of the request is changed. It
	 *            may not be null.
	 * @param inPath
	 *            the service path of the request. It may not be null.
	 * @param inRequestInfo
	 *            the request information to be submitted. It may not be null.
	 * @return the created service client
	 */
	private static IServiceClient createServiceClient(
			final Context inContext, final String inPath,
			final IRequestedAnnouncement inRequestInfo)
	{
		InstanceAsserter.assertNotNull(inRequestInfo, "request information");

		return clientFactory.createResourceSubmittionClient(inContext,
				inPath, (RequestedAnnouncement) inRequestInfo,
				MessageFormat.format(
						REQUEST_INFO_SUBMITION_ERROR, inRequestInfo));
	}
}
