package org.ai.shared.traveller.network.connection.rest.client;

import android.content.Context;

/**
 * The class represents a REST client used for DELETE http requests
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractDeleteClient extends AbstractSubmitClient
{
	/**
	 * Instantiates a new delete client
	 * 
	 * @param inContext
	 *            the used in the client's instantiation
	 * @param inPath
	 *            the service's path that the client uses
	 */
	public AbstractDeleteClient(final Context inContext,
			final String inPath)
	{
		super(inContext, RequestType.DELETE, inPath);
	}
}
