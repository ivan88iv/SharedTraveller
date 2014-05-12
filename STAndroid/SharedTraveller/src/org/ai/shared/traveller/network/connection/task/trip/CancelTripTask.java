package org.ai.shared.traveller.network.connection.task.trip;

import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.MainActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.util.Log;

/**
 * The class represents a network task used to cancel a trip on the server side
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class CancelTripTask extends AbstractNetworkTask<MainActivity, Void>
{
	/**
	 * Creates a new trip cancel task
	 * 
	 * @param inActivity
	 *            the activity used to create this task. It may not be null
	 * @param inAnnouncementId
	 *            the id of the announcement showing which trip would be
	 *            cancelled
	 * 
	 */
	public CancelTripTask(final MainActivity inActivity,
			final Long inAnnouncementId)
	{
		super(inActivity, constructServiceClient(inActivity, "trip/cancel",
				inAnnouncementId), Void.class);
	}

	@Override
	protected void onSuccess(Void inResult)
	{
		Log.d("CancelTripTask", "The trip was cancelled successfully.");
		getContext().onSuccessfulTripCancellation();
	}

	@Override
	protected void onError(int inStatusCode, ErrorResponse inError)
	{
		Log.d("CancelTripTask",
				"The trip could not be cancelled because of a problem.");
		getContext().onTripCancellationProblem();
	}

	/**
	 * The method constructs the service client used by the current task
	 * 
	 * @param inActivity
	 *            the activity used to construct the service client. It may not
	 *            be null
	 * @param inPath
	 *            the path of the service. It may not be null
	 * @param inAnnouncementId
	 *            the id of the announcement that is sent as a request parameter
	 *            by the service client. It may not be null.
	 * @return the constructed service client
	 */
	private static IServiceClient constructServiceClient(
			final MainActivity inActivity,
			final String inPath, final Long inAnnouncementId)
	{
		InstanceAsserter.assertNotNull(inAnnouncementId, "announcement id");

		final Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("announcementId", String.valueOf(inAnnouncementId));

		return clientFactory.createFormSubmitionClient(inActivity, inPath,
				parameters);
	}
}
