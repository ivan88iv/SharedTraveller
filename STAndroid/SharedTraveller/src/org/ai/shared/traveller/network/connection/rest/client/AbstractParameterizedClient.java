package org.ai.shared.traveller.network.connection.rest.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ai.shared.traveller.exceptions.ServiceConnectionException;

public abstract class AbstractParameterizedClient
        extends AbstractSubmitClient
{
    public AbstractParameterizedClient(final RequestTypes inType)
    {
        super(inType);
    }

    private static final String PARAM_ENCODING = "UTF-8";

    @Override
    protected String specifyContentType()
    {
        return "application/x-www-form-urlencoded";
    }

    @Override
    protected void submitData(final OutputStream inOutStream)
            throws ServiceConnectionException
    {
        try
        {
            final Map<String, String> params = prepareRequestParameters();
            final String encodedParams = encodeParameters(params);
            final BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(inOutStream, PARAM_ENCODING));
            try
            {
                writer.write(encodedParams);
                writer.flush();
            } finally
            {
                writer.close();
            }
        } catch (final IOException ioe)
        {
            throw new ServiceConnectionException("A problem occurred " +
                    "while preparing the request parameters.");
        }
    }

    /**
     * The method prepares the request parameters that are used in the request
     * 
     * @return the prepared request parameters
     */
    protected abstract Map<String, String> prepareRequestParameters();

    private String encodeParameters(final Map<String, String> inParams)
            throws UnsupportedEncodingException
    {
        final StringBuilder encodedResult = new StringBuilder();
        if (null != inParams)
        {
            final Iterator<Entry<String, String>> iterator =
                    inParams.entrySet().iterator();
            boolean addParamDelimiter = false;

            while (iterator.hasNext())
            {
                if (addParamDelimiter)
                {
                    encodedResult.append("&");
                } else
                {
                    addParamDelimiter = true;
                }

                final Map.Entry<String, String> entry = iterator.next();
                encodedResult.append(URLEncoder.encode(entry.getKey(),
                        PARAM_ENCODING));
                encodedResult.append("=");
                encodedResult.append(URLEncoder.encode(entry.getValue(),
                        PARAM_ENCODING));
            }
        }

        return encodedResult.toString();
    }
}
