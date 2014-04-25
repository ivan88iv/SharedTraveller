package org.ai.shared.traveller.task.request;

import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.request.AnnouncementRequestActivity;

/**
 * The class represents the common functionality for tasks that change a
 * request's status
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class ChangeRequestStatusTask
		extends AbstractNetworkTask<AnnouncementRequestActivity, Void>
{
	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated
	 * @param inUrl
	 *            the URL of the service being called on the server side
	 */
	public ChangeRequestStatusTask(
			final AnnouncementRequestActivity inActivity,
			final String inUrl)
	{
		super(inActivity, createServiceClient(inActivity, inUrl), Void.class);
	}

	/**
	 * The method creates and returns the service client used for changing the
	 * status of the specified request in the provided activity
	 * 
	 * @param inActivity
	 *            the activity which holds information about the specified
	 *            request whose status is to be changed
	 * @param inPath
	 *            the service path of the request
	 * @return the created POST REST client
	 */
	private static IServiceClient createServiceClient(
			final AnnouncementRequestActivity inActivity,
			final String inPath)
	{
		final Map<String, String> parameters =
				new HashMap<String, String>();
		parameters.put("id",
				String.valueOf(inActivity.getLastSelectedRequestId()));
		final IServiceClientFactory clientFactory =
				DomainManager.getInstance().getServiceClientFactory();

		return clientFactory.createFormSubmitionClient(inActivity, inPath,
				parameters);
	}
}
