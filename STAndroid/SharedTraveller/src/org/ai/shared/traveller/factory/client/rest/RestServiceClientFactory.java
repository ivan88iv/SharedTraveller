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
		InstanceAsserter.assertNotNull(inContext, "context");
		InstanceAsserter.assertNotNull(inServicePath, "service path");

		return new SimpleClient(inContext, RequestType.GET, inServicePath);
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
}
