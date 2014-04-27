package org.ai.shared.traveller.network.connection.rest.client;

import android.content.Context;

/**
 * The class represents a REST client that uses some user-defined request
 * parameters and sends the information via a POST request to the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractPostClient extends AbstractParameterizedClient
{
	/**
	 * The constructor instantiates a new POST REST client
	 * 
	 * @param inContext
	 *            the context into which the client is created. It may not be
	 *            null
	 * @param inServerPath
	 *            the server path of the service. It may not be null
	 */
	public AbstractPostClient(final Context inContext,
			final String inServerPath)
	{
		super(inContext, RequestType.POST, inServerPath);
	}
}
