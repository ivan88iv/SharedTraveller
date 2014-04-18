package org.ai.shared.traveller.network.connection.response;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;

import org.ai.shared.traveller.exceptions.ParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * The class represents the functionality needed to parse a server response so
 * that it can be used throughout the application
 * 
 * @author "Ivan Ivanov"
 * 
 * @param <T>
 *            the type of the server response result
 */
public class ServerResponseParser<T>
{
    private static final String NULL_IS = "The input stream cannot be null.";

    private final TypeReference<T> elemTypeRef;

    final ObjectMapper mapper = new ObjectMapper();

    /**
     * The constructor instantiates a new server response parser
     * 
     * @param inClass
     *            the of the result returned from the server response
     */
    public ServerResponseParser(final Class<T> inClass)
    {
        elemTypeRef = new TypeReference<T>()
        {
            @Override
            public Type getType()
            {
                return inClass;
            }
        };
    }

    /**
     * The constructor instantiates a new server response parser
     * 
     * @param inTypeReference
     *            the type reference that represents the type of the data to be
     *            parsed
     */
    public ServerResponseParser(final TypeReference<T> inTypeReference)
    {
        elemTypeRef = inTypeReference;
    }

    /**
     * The method parses the result contained in the given input stream
     * 
     * @param inResponseCode
     *            the status code of the server response
     * @param inIS
     *            the input stream that contains the result to be parsed. It
     *            must not be null.
     * @return the parsed server response
     * @throws ParseException
     *             if an error occurs while trying to parse the data contained
     *             in the input stream
     */
    public ServerResponse<T> parseResponse(final int inResponseCode,
            final PushbackInputStream inIS) throws ParseException
    {
        assert null != inIS : NULL_IS;

        ServerResponse<T> parsedResponse = new ServerResponse<T>(null,
                inResponseCode);

        try
        {
            T serviceData = null;
            if (!isEmpty(inIS))
            {
                serviceData = mapper.readValue(inIS, elemTypeRef);
            }
            parsedResponse = new ServerResponse<T>(serviceData, inResponseCode);
        } catch (final IOException ioe)
        {
            throw new ParseException("Could not parse service response.", ioe);
        }

        return parsedResponse;
    }

    /**
     * The method checks if the passed stream is empty and returns true if so.
     * 
     * @param inStream
     *            the input stream to be checked. It may not be null.
     * @return true if the stream is empty and false otherwise
     * @throws IOException
     *             if a problem occurs while checking the stream
     */
    private boolean isEmpty(final PushbackInputStream inStream)
            throws IOException
    {
        final int firstByte = inStream.read();
        final boolean empty = -1 == firstByte;
        inStream.unread(firstByte);
        return empty;
    }
}
