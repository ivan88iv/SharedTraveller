package org.ai.shared.traveller.factory.client;

import java.util.Map;

import org.ai.shared.traveller.network.connection.client.IServiceClient;

import android.content.Context;

/**
 * The interface represents the common functionality for classes that are used
 * as service client factories in the application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IServiceClientFactory
{
	/**
	 * Creates a new simple client used to make a call to the specified service
	 * path and return an appropriate response from it
	 * 
	 * @param inContext
	 *            the application context used in the creation. It may not be
	 *            null.
	 * @param inServicePath
	 *            the path of the service to be called. It may not be null
	 * @return the created service client
	 */
	IServiceClient createSimpleClient(final Context inContext,
			final String inServicePath);

	/**
	 * Creates a new service client used to make a call to the specified service
	 * path and return an appropriate response from it. There are some
	 * parameters to be submitted for this call, too.
	 * 
	 * @param inContext
	 *            the application context used in the creation. It may not be
	 *            null.
	 * @param inServicePath
	 *            the path of the service to be called. It may not be null
	 * @param inParameters
	 *            the parameters that are submitted.
	 * @return the created service client
	 */
	IServiceClient createFormSubmitionClient(final Context inContext,
			final String inServicePath,
			final Map<String, String> inParameters);

	/**
	 * Creates a new service client used to insert a new resource in the server
	 * by calling the specified service.
	 * 
	 * @param inContext
	 *            the application context used in the creation. It may not be
	 *            null.
	 * @param inServicePath
	 *            the path of the service to be called. It may not be null
	 * @param inNewInstance
	 *            the new resource to be sent to the service. It may not be
	 *            null.
	 * @param inErrorMsg
	 *            an error message to be displayed in the stack trace if a
	 *            problem occurs while trying to send the resource
	 * @return the created service client
	 * 
	 * @param <T>
	 *            the type of the new resource to be sent to the service
	 */
	<T> IServiceClient createNewResourceClient(final Context inContext,
			final String inServicePath,
			final T inNewInstance, final String inErrorMsg);

	/**
	 * The method creates a new client responsible for resource deletion in the
	 * server part
	 * 
	 * @param inContext
	 *            the context used in the creation. It may not be null.
	 * @param inServicePath
	 *            the path of the deletion service called. It may not be null.
	 * @return the newly created service client
	 */
	IServiceClient createResourceDeletionClient(final Context inContext,
			final String inServicePath);
}
