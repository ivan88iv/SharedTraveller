package org.ai.shared.traveller.network.connection.rest.client;

import java.net.HttpURLConnection;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

import android.content.Context;

/**
 * The class represents a simple service client that does not apply any
 * additional service call settings
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class SimpleClient extends AbstractRestClient
{
	/**
	 * The constructor creates a new Simple REST service client
	 * 
	 * @param inContext
	 *            the android context into which this client is created. It may
	 *            not be null.
	 * @param inType
	 *            the method type of the request to be performed
	 * @param inServerPath
	 *            the server path of the service called by this client. It may
	 *            not be null
	 */
	public SimpleClient(final Context inContext,
			final RequestType inType, final String inServerPath)
	{
		super(inContext, inType, inServerPath);
	}

	@Override
	protected void prepareCallSepcificSettings(
			final HttpURLConnection inConnection)
			throws ServiceConnectionException
	{
		// No additional settings are required
	}
}
