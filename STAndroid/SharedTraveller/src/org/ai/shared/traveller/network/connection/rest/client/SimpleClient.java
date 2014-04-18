package org.ai.shared.traveller.network.connection.rest.client;

import java.net.HttpURLConnection;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

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
     * @param inType
     *            the method type of the request to be performed
     */
    public SimpleClient(final RequestTypes inType)
    {
        super(inType);
    }

    @Override
    protected void prepareCallSepcificSettings(
            final HttpURLConnection inConnection)
            throws ServiceConnectionException
    {
        // No additional settings are required
    }
}
