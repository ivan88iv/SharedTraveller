package org.ai.shared.traveller.network.connection.response;

import org.shared.traveller.rest.domain.ErrorResponse;

/**
 * The class represents the parsed response from the server instance
 * 
 * @author Ivan
 * 
 * @param <T>
 *            the type of the response body returned from the server
 */
public class ServerResponse<T>
{
    private final T responseBody;

    private final int statusCode;

    private final ErrorResponse errorResponse;

    /**
     * Instantiates a new server response
     * 
     * @param inContent
     *            the body of the server response
     * @param inCode
     *            the status code of the server response
     */
    public ServerResponse(final T inContent, final int inCode)
    {
        responseBody = inContent;
        statusCode = inCode;
        errorResponse = null;
    }

    /**
     * The constructor creates a new response instance
     * 
     * @param inCode
     *            the status code of the server response
     * @param inError
     *            the error response returned from the server
     */
    public ServerResponse(final int inCode, final ErrorResponse inError)
    {
        responseBody = null;
        statusCode = inCode;
        errorResponse = inError;
    }

    /**
     * Returns the status code of the last server response
     * 
     * @return the status code of the last server response
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Returns the body associated with a server response or null if an error
     * has occurred on the server side
     * 
     * @return the body associated with a server response or null if an error
     *         has occurred on the server side
     */
    public T getResponseBody()
    {
        return responseBody;
    }

    /**
     * Returns a representation of the error on the server side or null if none
     * has occurred
     * 
     * @return a representation of the error on the server side or null if none
     *         has occurred
     */
    public ErrorResponse getErrorResponse()
    {
        return errorResponse;
    }
}
