package org.ai.shared.traveller.network.connection.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.shared.traveller.rest.domain.ErrorResponse;

/**
 * The class contains the general functionality used to make a service call
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractRestClient
{
	private static final String NO_URL = "No URL is supplied.";

	private static final String NO_PARSER = "No response pareser is passed.";

	private static final String NULL_REQUEST_TYPE = "No request type passed.";

	private final RequestTypes type;

	/**
	 * The constructor creates a new abstract rest client
	 * 
	 * @param inType
	 *            the request type made by the client
	 */
	public AbstractRestClient(final RequestTypes inType)
	{
		assert null != inType : NULL_REQUEST_TYPE;

		type = inType;
	}

	/**
	 * The method is used to call a service
	 * 
	 * @param inURL
	 *            the URL of the service
	 * @param inResponseParser
	 *            the parser of the service response
	 * @return the parser server response
	 * @throws ServiceConnectionException
	 *             if no proper connection could be made to the server
	 * @throws ParseException
	 *             if a problem occurs while trying to parse the server result
	 */
	public <T> ServerResponse<T> callService(final URL inURL,
			final ServerResponseParser<T> inResponseParser)
			throws ServiceConnectionException, ParseException
	{
		assert null != inURL : NO_URL;
		assert null != inResponseParser : NO_PARSER;

		ServerResponse<T> parsedResponse = null;
		final HttpURLConnection connection = connectService(inURL);

		try
		{
			InputStream inputStream = null;
			int resposeCode = 200;
			String responseMsg = null;

			try
			{
				resposeCode = connection.getResponseCode();
				if (resposeCode < 400)
				{
					inputStream = connection.getInputStream();
				} else
				{
					responseMsg = connection.getResponseMessage();
				}
			} catch (final IOException ioe)
			{
				throw new ServiceConnectionException(
						"No result is returned from the service call.", ioe);
			}

			parsedResponse = parseServerResponse(inputStream,
					resposeCode, responseMsg, inResponseParser);
		} finally
		{
			connection.disconnect();
		}

		return parsedResponse;
	}

	/**
	 * The method is used to prepare the specific settings for the current
	 * service call
	 * 
	 * @param inConnection
	 *            the service connection for the call
	 * @throws ServiceConnectionException
	 *             if a problem occurs while trying to prepare the additional
	 *             connection settings
	 */
	protected abstract void prepareCallSepcificSettings(
			final HttpURLConnection inConnection)
			throws ServiceConnectionException;

	/**
	 * The method makes the service connection
	 * 
	 * @param inURL
	 *            the URL of the service
	 * @return the newly established connection
	 * @throws ServiceConnectionException
	 *             if a problem occurs while trying to open a new service
	 *             connection
	 */
	private HttpURLConnection connectService(final URL inURL)
			throws ServiceConnectionException
	{
		HttpURLConnection connection = null;

		try
		{
			connection = (HttpURLConnection) inURL.openConnection();
		} catch (final IOException ioe)
		{
			throw new ServiceConnectionException(
					"Could not open service connection.", ioe);
		}

		connection.setReadTimeout(10000);
		connection.setConnectTimeout(15000);
		connection.setDoInput(true);
		try
		{
			connection.setRequestMethod(type.getCode());
		} catch (final ProtocolException pe)
		{
			throw new ServiceConnectionException(
					"Illegal request method used.", pe);
		}

		prepareCallSepcificSettings(connection);

		try
		{
			connection.connect();
		} catch (final IOException ioe)
		{
			throw new ServiceConnectionException(
					"An error occurred while trying to connect to the service.",
					ioe);
		}

		return connection;
	}

	/**
	 * The method closes the input stream that holds the service response
	 * 
	 * @param inStream
	 *            the stream which is to be closed
	 * @param inOriginalException
	 *            the instance represents an exception that has resulted just
	 *            before the stream close moment or null if none has been
	 *            present
	 * @throws ServiceConnectionException
	 *             if no parse exception has been thrown and if we are unable to
	 *             close the stream
	 */
	private void closeResultStream(final InputStream inStream,
			final Exception inOriginalException)
			throws ServiceConnectionException
	{
		try
		{
			inStream.close();
		} catch (final IOException ioe)
		{
			if (null == inOriginalException)
			{
				throw new ServiceConnectionException(
						"Could not close the service connection result stream",
						ioe);
			}
		}
	}

	/**
	 * The method produces the server response in an appropriate type
	 * 
	 * @param inInputStream
	 *            the input stream in which the server response is contained. If
	 *            it is null an error is considered to be received from the
	 *            server.
	 * @param inResponseCode
	 *            the response code coming from the server
	 * @param inResponseMsg
	 *            the response message coming from the server. It is included in
	 *            the parsed result only if an error response code is returned.
	 * @param inResponseParser
	 *            the parser used for the result generation process
	 * @return the parsed response coming from the server
	 * @throws ServiceConnectionException
	 *             if we cannot close the open input stream
	 * @throws ParseException
	 *             if an error occurs during server response parsing
	 */
	private <T> ServerResponse<T> parseServerResponse(
			final InputStream inInputStream,
			final int inResponseCode,
			final String inResponseMsg,
			final ServerResponseParser<T> inResponseParser)
			throws ServiceConnectionException
	{
		ServerResponse<T> parsedResponse = null;

		if (null == inInputStream)
		{
			parsedResponse = new ServerResponse<T>(inResponseCode,
					new ErrorResponse(inResponseMsg));
		} else
		{
			ServiceConnectionException thrownException = null;
			try
			{
				final PushbackInputStream pushBackStream =
						new PushbackInputStream(inInputStream);
				ParseException parseException = null;
				try
				{
					parsedResponse = inResponseParser.parseResponse(
							inResponseCode, pushBackStream);
				} catch (final ParseException pe)
				{
					parseException = pe;
					throw new ServiceConnectionException(
							"A problem occurred while trying to "
									+ "parse the server's response.", pe);
				} finally
				{
					closeResultStream(pushBackStream, parseException);
				}
			} catch (final ServiceConnectionException sce)
			{
				thrownException = sce;
			} finally
			{
				closeResultStream(inInputStream, thrownException);
			}
		}

		return parsedResponse;
	}
}
