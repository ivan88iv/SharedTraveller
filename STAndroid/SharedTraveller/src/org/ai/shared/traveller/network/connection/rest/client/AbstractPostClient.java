package org.ai.shared.traveller.network.connection.rest.client;

import android.content.Context;

/**
 * The class represents a REST client that submits information to the server via
 * a POST request
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractPostClient extends AbstractSubmitClient
{
	/**
	 * The constructor instantiates a new POST REST client
	 * 
	 * @param inContext
	 *            the context into which the client is created. It may not be
	 *            null
	 * @param inServicePath
	 *            the path of the service on the server. It may not be null
	 */
	public AbstractPostClient(final Context inContext,
			final String inServicePath)
	{
		super(inContext, RequestType.POST, inServicePath);
	}
}
