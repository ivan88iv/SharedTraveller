package org.ai.shared.traveller.task.request;

import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.utility.InstanceAsserter;

import android.app.Activity;

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
		extends AbstractNetworkTask<T, Void>
{
	/**
	 * The constructor initializes a new request acceptance asynchronous task
	 * 
	 * @param inActivity
	 *            the activity to which the task is associated. It may not be
	 *            null.
	 * @param inPath
	 *            the path of the service being called. It may not be null.
	 * @param inRequestId
	 *            the id of the request whose status is to be changed. It may
	 *            not be null
	 */
	public ChangeRequestStatusTask(final T inActivity,
			final String inPath, final Long inRequestId)
	{
		super(inActivity, createServiceClient(inActivity, inPath, inRequestId),
				Void.class);
	}

	/**
	 * The method creates and returns the service client used for changing the
	 * status of the specified request in the provided activity
	 * 
	 * @param inActivity
	 *            the activity which holds information about the specified
	 *            request whose status is to be changed. It may not be null.
	 * @param inPath
	 *            the service path of the request. It may not be null.
	 * @param inRequestId
	 *            the id of the request to be changed. It may not be null.
	 * @return the created POST REST client
	 */
	private static IServiceClient createServiceClient(
			final Activity inActivity,
			final String inPath, final Long inRequestId)
	{
		InstanceAsserter.assertNotNull(inRequestId, "request id");

		final Map<String, String> parameters =
				new HashMap<String, String>();
		parameters.put("id", String.valueOf(inRequestId));
		final IServiceClientFactory clientFactory =
				DomainManager.getInstance().getServiceClientFactory();

		return clientFactory.createFormSubmitionClient(inActivity, inPath,
				parameters);
	}
}
