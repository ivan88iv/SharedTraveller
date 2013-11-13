package org.ai.shared.traveller.network.connection.rest.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

public abstract class SubmitClient extends AbstractRestClient
{

    public SubmitClient(final RequestTypes inType)
    {
        super(inType);
    }

    @Override
    protected void prepareCallSepcificSettings(
            final HttpURLConnection inConnection)
            throws ServiceConnectionException
    {
        inConnection.setDoOutput(true);

        try
        {
            final OutputStream outStream = inConnection.getOutputStream();
            try
            {
                submitData(outStream);
            } finally
            {
                outStream.close();
            }
        } catch (final IOException ioe)
        {
            throw new ServiceConnectionException(
                    "An error occurred while trying to send some data to the service.",
                    ioe);
        }
    }

    protected abstract void submitData(final OutputStream inOutStream)
            throws IOException;
}
