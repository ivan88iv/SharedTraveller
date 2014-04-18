package org.ai.shared.traveller.network.connection.rest.client;


/**
 * The class is responsible for performing PUT REST calls to the server side. It
 * is meant to be extended and amended when a concrete service call is made.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public abstract class AbstractPutClient extends AbstractSubmitClient
{
    /**
     * The constructor creates a new PUT REST service client
     */
    public AbstractPutClient()
    {
        super(RequestTypes.PUT);
    }
}
