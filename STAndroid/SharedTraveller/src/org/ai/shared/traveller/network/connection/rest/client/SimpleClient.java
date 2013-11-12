package org.ai.shared.traveller.network.connection.rest.client;

import java.net.HttpURLConnection;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

public class SimpleClient extends AbstractRestClient
{
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
