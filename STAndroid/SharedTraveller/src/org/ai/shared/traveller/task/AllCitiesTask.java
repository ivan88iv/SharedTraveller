package org.ai.shared.traveller.task;

import java.util.List;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.codehaus.jackson.type.TypeReference;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;

public class AllCitiesTask extends AbstractNetworkTask<List<String>>
{
    private final ICityComponentsPreparator preparator;

    public AllCitiesTask(final Activity inActivity,
            final AbstractRestClient inClient,
            final ICityComponentsPreparator inPreparator)
    {
        super(inActivity, "stserver/cities/all", inClient,
                new TypeReference<List<String>>()
                {
                });
        preparator = inPreparator;
    }

    @Override
    protected void onError(final int inStatusCode, final ErrorResponse inError)
    {
        Log.d("AllCitiesTask",
                "Unsuccessful extraction of the cities available");
    }

    @Override
    protected void onSuccess(final List<String> inResult)
    {
        Log.d("AllCitiesTask",
                "Successful extraction of the cities available");

        if (null != inResult)
        {
            final String[] cityNames = new String[inResult.size()];
            int currNameInd = 0;
            for (final String currName : inResult)
            {
                cityNames[currNameInd++] = currName;
            }

            preparator.prepareComponents(cityNames);
        }
    }
}
