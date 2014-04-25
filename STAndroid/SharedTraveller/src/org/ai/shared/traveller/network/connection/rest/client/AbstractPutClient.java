package org.ai.shared.traveller.network.connection.rest.client;

import android.content.Context;

/**
 * The class is responsible for performing PUT REST calls to the server side. It
 * is meant to be extended and amended when a concrete service call is made.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractPutClient extends AbstractSubmitClient
{
	/**
	 * The constructor creates a new PUT REST service client
	 * 
	 * @param inContext
	 *            the android context into which this client is created. It may
	 *            not be null.
	 * @param inServerPath
	 *            the server path of the service called by this client. It may
	 *            not be null
	 */
	public AbstractPutClient(final Context inContext,
			final String inServerPath)
	{
		super(inContext, RequestType.PUT, inServerPath);
	}
}
