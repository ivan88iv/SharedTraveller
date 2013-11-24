package org.ai.shared.traveller.network.connection.response;

import java.io.IOException;
import java.io.InputStream;

import org.ai.shared.traveller.exceptions.ParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.shared.traveller.rest.domain.AnnouncementsList;

public class ServerResponseParser<T>
{
	private static final String NULL_IS = "The input stream cannot be null.";

	public ServerResponse<T> parseResponse(final InputStream inIS) throws ParseException
	{
		assert null != inIS : NULL_IS;

		final ObjectMapper mapper = new ObjectMapper();
		final TypeReference<AnnouncementsList> typeRef = new TypeReference<AnnouncementsList>()
		{
		};

		ServerResponse<T> parsedResponse = new ServerResponse<T>(200, null);
		try
		{
			final T serviceData = (T) mapper.readValue(inIS, typeRef);
			parsedResponse = new ServerResponse<T>(serviceData, 200, "OLE");
		}
		catch (final IOException ioe)
		{
			throw new ParseException("Could not parse service response.", ioe);
		}

		return parsedResponse;
	}
}
