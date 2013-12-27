package org.ai.shared.traveller.network.connection.rest.client;

public abstract class AbstractPutClient extends AbstractSubmitClient
{
    public AbstractPutClient()
    {
        super(RequestTypes.PUT);
    }
}
