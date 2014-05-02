package org.ai.shared.traveller.network.connection.task;

import java.util.List;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The class is used to extract all cities and apply them to UI components.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class AllCitiesTask extends AbstractNetworkTask<Activity, List<String>>
{
	private final ICityComponentsPreparator preparator;

	/**
	 * The constructor instantiates a new task for cities extraction
	 * 
	 * @param inActivity
	 *            the activity to which the task is attached
	 * @param inClient
	 *            the service client used for cities extraction
	 * @param inPreparator
	 *            the instance that is responsible for applying the extracted
	 *            city values to UI components
	 */
	public AllCitiesTask(final Activity inActivity,
			final IServiceClient inClient,
			final ICityComponentsPreparator inPreparator)
	{
		super(inActivity, inClient,
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
