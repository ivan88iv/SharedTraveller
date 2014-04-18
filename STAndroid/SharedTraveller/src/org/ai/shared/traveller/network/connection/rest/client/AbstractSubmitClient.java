package org.ai.shared.traveller.network.connection.rest.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

/**
 * The class represents a service client that is used to make a service submit
 * call
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractSubmitClient extends AbstractRestClient
{
    private static final String DEFAULT_CONTENT_TYPE =
            "application/json";

    /**
     * The constructor creates a new submit client
     * 
     * @param inType
     *            the type of the request used by the client
     */
    public AbstractSubmitClient(final RequestTypes inType)
    {
        super(inType);
    }

    /**
     * The method specifies the Content-Type used for the current request
     * 
     * @return the Content-Type used for the current request
     */
    protected String specifyContentType()
    {
        return DEFAULT_CONTENT_TYPE;
    }

    @Override
    protected void prepareCallSepcificSettings(
            final HttpURLConnection inConnection)
            throws ServiceConnectionException
    {
        inConnection.setDoOutput(true);
        inConnection.setRequestProperty("Content-Type", specifyContentType());
        inConnection.setUseCaches(false);

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

    /**
     * The method adds the data to be submitted to the specified output stream
     * 
     * @param inOutStream
     *            the output stream to submit the data to. It must not be null.
     * 
     * @throws IOException
     *             if a problem occurs while trying to add the data to be
     *             submitted to the stream
     */
    protected abstract void submitData(final OutputStream inOutStream)
            throws ServiceConnectionException;
}
