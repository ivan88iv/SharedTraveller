package org.ai.shared.traveller.factory.client.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPostClient;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPutClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestType;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The class represents a factory class for REST service clients.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestServiceClientFactory implements IServiceClientFactory
{
	@Override
	public IServiceClient createSimpleClient(final Context inContext,
			final String inServicePath)
	{
		return createSimpleClient(inContext, inServicePath, RequestType.GET);
	}

	@Override
	public IServiceClient createFormSubmitionClient(final Context inContext,
			final String inServicePath, final Map<String, String> inParameters)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inServicePath, "service path");

		return new AbstractPostClient(inContext, inServicePath)
		{
			@Override
			protected Map<String, String>
					prepareRequestParameters()
			{
				return DeepCopier.copy(inParameters);
			}
		};
	}

	@Override
	public <T> IServiceClient createNewResourceClient(final Context inContext,
			final String inServicePath, final T inNewInstance,
			final String inErrorMsg)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inServicePath, "service path");
		InstanceAsserter.assertNotNull(inNewInstance, "new resource");

		return new AbstractPutClient(inContext, inServicePath)
		{
			@Override
			protected void submitData(final OutputStream inStream)
					throws ServiceConnectionException
			{
				final ObjectMapper writer = new ObjectMapper();

				try
				{
					writer.writeValue(inStream, inNewInstance);
				} catch (final IOException ioe)
				{
					throw new ServiceConnectionException(
							inErrorMsg, ioe);
				}
			}
		};
	}

	@Override
	public IServiceClient createResourceDeletionClient(final Context inContext,
			final String inServicePath)
	{
		return createSimpleClient(inContext, inServicePath, RequestType.DELETE);
	}

	/**
	 * The method creates a new simple client for the specified context, service
	 * path and request type
	 * 
	 * @param inContext
	 *            the context for which a new REST client is created. It may not
	 *            be null.
	 * @param inServicePath
	 *            the service path for which the current client is responsible.
	 *            It may not be null.
	 * @param inRequestType
	 *            the type of the Http request performed by this client.
	 * @return the newly created simple client
	 */
	private SimpleClient createSimpleClient(final Context inContext,
			final String inServicePath, final RequestType inRequestType)
	{
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inServicePath, "service path");

		return new SimpleClient(inContext, inRequestType, inServicePath);
	}
}
