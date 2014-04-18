package org.ai.shared.traveller.network.connection.rest.client;

/**
 * The class represents a REST client that uses some user-defined request
 * parameters and sends the information via a POST request to the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractPostClient extends AbstractParameterizedClient
{
    /**
     * The constructor instantiates a new POST REST client
     */
    public AbstractPostClient()
    {
        super(RequestTypes.POST);
    }
}
