package org.ai.shared.traveller.task;

import java.util.List;

import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.app.Activity;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The class represents a task which is used to extract the vehicles for a
 * specific customer
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class UserVehiclesTask extends
		AbstractNetworkTask<Activity, List<String>>
{
	private final IVehicleComponentsPreparator preparator;

	/**
	 * The constructor instantiates a new vehicles extraction task
	 * 
	 * @param inActivity
	 *            the activity to which the task belongs
	 * @param inClient
	 *            the REST client used for vehicles extraction
	 * @param inUsername
	 *            the user name of the customer whose vehicles are extracted
	 * @param inPreparator
	 *            the instance responsible for applying the extracted vehicles
	 *            to the UI components
	 */
	public UserVehiclesTask(final Activity inActivity,
			final AbstractRestClient inClient,
			final String inUsername,
			final IVehicleComponentsPreparator inPreparator)
	{
		super(inActivity, "vehicles/" + inUsername, inClient,
				new TypeReference<List<String>>()
				{
				});

		preparator = inPreparator;
	}

	@Override
	protected void onSuccess(final List<String> inResult)
	{
		Log.d("UserVehiclesTask", "Succeesful extraction of user vehicles");
		if (null != inResult)
		{
			final int namesCnt = inResult.size();
			final String[] vehicleNames = new String[namesCnt];
			for (int nameInd = 0; nameInd < namesCnt; ++nameInd)
			{
				vehicleNames[nameInd] = inResult.get(nameInd);
			}

			preparator.prepareComponents(vehicleNames);
		}
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("UserVehiclesTask", "Unsucceesful extraction of user vehicles");
	}
}
