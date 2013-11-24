package org.ai.shared.traveller.network.connection.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;

public abstract class AbstractRestClient
{
	private static final String NO_URL = "No URL is supplied.";

	private static final String NO_PARSER = "No response pareser is passed.";

	private static final String NULL_REQUEST_TYPE = "No request type passed.";

	private final RequestTypes type;

	public AbstractRestClient(final RequestTypes inType)
	{
		assert null != inType : NULL_REQUEST_TYPE;

		type = inType;
	}

	public <T> ServerResponse<T> callService(final URL inURL, final ServerResponseParser<T> inResponseParser) throws ServiceConnectionException, ParseException
	{
		assert null != inURL : NO_URL;
		assert null != inResponseParser : NO_PARSER;

		ServerResponse<T> parsedResponse = null;
		final HttpURLConnection connection = connectService(inURL);

		try
		{
			InputStream inputStream = null;

			try
			{
				System.out.println("===================================");
				System.out.println("code: " + connection.getResponseCode());
				System.out.println("message " + connection.getResponseMessage());
				System.out.println("===================================");
				inputStream = connection.getInputStream();
			}
			catch (final IOException ioe)
			{
				throw new ServiceConnectionException("No result is returned from the service call.", ioe);
			}

			ParseException parseException = null;
			try
			{
				parsedResponse = inResponseParser.parseResponse(inputStream);
			}
			catch (final ParseException pe)
			{
				parseException = pe;
			}
			finally
			{
				closeResultStream(inputStream, parseException);
			}

		}
		finally
		{
			connection.disconnect();
		}

		return parsedResponse;
	}

	private HttpURLConnection connectService(final URL inURL) throws ServiceConnectionException
	{
		HttpURLConnection connection = null;

		try
		{
			connection = (HttpURLConnection) inURL.openConnection();
		}
		catch (final IOException ioe)
		{
			throw new ServiceConnectionException("Could not open service connection.", ioe);
		}

		connection.setReadTimeout(10000);
		connection.setConnectTimeout(15000);
		connection.setDoInput(true);
		try
		{
			connection.setRequestMethod(type.getCode());
		}
		catch (final ProtocolException pe)
		{
			throw new ServiceConnectionException("Illegal request method used.", pe);
		}

		prepareCallSepcificSettings(connection);

		try
		{
			connection.connect();
		}
		catch (final IOException ioe)
		{
			throw new ServiceConnectionException("An error occurred while trying to connect to the service.", ioe);
		}

		return connection;
	}

	private void closeResultStream(final InputStream inStream, final ParseException inParseException) throws ServiceConnectionException
	{
		try
		{
			inStream.close();
		}
		catch (final IOException ioe)
		{
			if (null == inParseException)
			{
				throw new ServiceConnectionException("Could not close the service connection result stream", ioe);
			}
		}
	}

	protected abstract void prepareCallSepcificSettings(final HttpURLConnection inConnection) throws ServiceConnectionException;
}
