package org.ai.shared.traveller.network.connection.task.trip;

import java.text.MessageFormat;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.trip.MyTripsActivity;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

/**
 * The class represents a network task used to extract trips for the specified
 * passenger
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class TripExtractionTask extends
		AbstractNetworkTask<MyTripsActivity, Void>
{
	private static final String SUCCESSFUL_EXTRACTION =
			"Passenger trips have been successfully extracted.";

	private static final String EXTRACTION_PROBLEM =
			"A problem occurred while trying to extract "
					+ "the passenger's trips: error code {0}, message {1}";

	/**
	 * Instantiates a new task for obtaining trips for a particular passenger
	 * 
	 * @param inActivity
	 *            the activity for viewing the obtained trips. It may not be
	 *            null
	 * @param inPassengerId
	 *            the id of the passenger whose trips are retrieved. It may not
	 *            be null
	 */
	public TripExtractionTask(final MyTripsActivity inActivity,
			final Long inPassengerId)
	{
		super(inActivity, constructServiceClient(inActivity, inPassengerId),
				Void.class);
	}

	@Override
	protected void onSuccess(Void inResult)
	{
		Log.d("PassengerNtfRemovalTask", SUCCESSFUL_EXTRACTION);
		getContext().onSuccessfulTripsExtraction();
	}

	@Override
	protected void onError(int inStatusCode, ErrorResponse inError)
	{
		Log.d("PassengerNtfRemovalTask", MessageFormat.format(
				EXTRACTION_PROBLEM, inStatusCode, inError));
		getContext().onTripsExtractionProblem();
	}

	/**
	 * The method constructs the service client to be used by the current task
	 * 
	 * @param inContext
	 *            the context used for creating the service client. It may not
	 *            be null
	 * @param inPassengerId
	 *            the id of the passenger for which notifications are removed.
	 *            It may not be null
	 * @return the client used to connect to the service that removes the
	 *         passenger's notifications
	 */
	private static IServiceClient constructServiceClient(
			final Context inContext, final Long inPassengerId)
	{
		InstanceAsserter.assertNotNull(inPassengerId, "passenger's id");

		return clientFactory.createFormSubmitionClient(
				inContext, "trip/all/" + inPassengerId, null);
	}
}
