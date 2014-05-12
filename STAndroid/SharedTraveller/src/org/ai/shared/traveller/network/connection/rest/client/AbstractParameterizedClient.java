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

import android.content.Context;

/**
 * This client is responsible for executing POST REST requests which send a
 * number of simple form parameters to the service
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractParameterizedClient
		extends AbstractSubmitClient
{
	private static final String PARAM_ENCODING = "UTF-8";

	private static final String REQUEST_PARAMS_ENCODING_FAILED =
			"The encoding of the request parameters was not successful.";

	/**
	 * The constructor instantiates a new parameterized client
	 * 
	 * @param inContext
	 *            the context into which the client is created. It may not be
	 *            null
	 * @param inServerPath
	 *            the server path of the service. It may not be null
	 */
	public AbstractParameterizedClient(final Context inContext,
			final String inServerPath)
	{
		super(inContext, RequestType.POST, inServerPath);
	}

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

	/**
	 * The method encodes the passed parameters in an appropriate format so that
	 * they could be passed to the service
	 * 
	 * @param inParams
	 *            the request parameters to be encoded
	 * @return the encoded string
	 * @throws ServiceConnectionException
	 *             if the parameters could not be encoded correctly
	 */
	private String encodeParameters(final Map<String, String> inParams)
			throws ServiceConnectionException
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
				try
				{
					encodedResult.append(URLEncoder.encode(entry.getKey(),
							PARAM_ENCODING));
					encodedResult.append("=");
					encodedResult.append(URLEncoder.encode(entry.getValue(),
							PARAM_ENCODING));
				} catch (final UnsupportedEncodingException uee)
				{
					throw new ServiceConnectionException(
							REQUEST_PARAMS_ENCODING_FAILED, uee);
				}
			}
		}

		return encodedResult.toString();
	}
}
