package org.ai.shared.traveller;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.DummyRequest;

public class DummyTaskGet extends AbstractNetworkTask<DummyRequest>
{

    public DummyTaskGet(final AbstractRestClient inClient)
    {
        super("http://localhost:8080/stserver/dummy/asdadsad", inClient);
    }

    @Override
    protected void onSuccess(final DummyRequest inResult)
    {
        System.out.println("SUCCESS: " + inResult);
    }

    @Override
    protected void onError(final int inStatusCode, final String inMessage)
    {
        System.out.println("ERROR: " + inStatusCode + "  " + inMessage);
    }

}
