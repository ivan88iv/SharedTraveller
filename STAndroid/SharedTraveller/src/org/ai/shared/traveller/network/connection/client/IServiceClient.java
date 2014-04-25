package org.ai.shared.traveller.network.connection.client;

import java.net.URL;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;

/**
 * The interface represents the common functionality for service clients that
 * are used to make service calls
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IServiceClient
{
	/**
	 * The method returns the service URL
	 * 
	 * @return the service URL
	 */
	URL getServiceAddress();

	/**
	 * The method makes the call to the service
	 * 
	 * @param inParser
	 *            the response parser used for this call
	 * @return the response from the service
	 * @throws ServiceConnectionException
	 *             if a problem occurs while trying to realize the service
	 *             connection
	 * @throws ParseException
	 *             if a problem occurs while trying to parse the response from
	 *             the service
	 */
	<T> ServerResponse<T> callService(final ServerResponseParser<T> inParser)
			throws ServiceConnectionException, ParseException;
}
