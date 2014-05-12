package org.ai.shared.traveller.trip;

import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.task.trip.TripExtractionTask;
import org.ai.shared.traveller.notification.NotificationRemover;
import org.ai.shared.traveller.notification.NotifiedIntentManager;

import android.widget.Toast;

/**
 * The activity is responsible for visualizing user trips
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class MyTripsActivity extends AbstractNetworkActivity
{
	private static final String SUCCESSFUL_EXTRACTION =
			"Your trips are successfully extracted";

	private static final String EXTRACTION_PROBLEM =
			"Your trips could not be loaded because of a server problem.";

	private static final String TRIP_EXTRACTION_TASK_KEY =
			"tripExtraction";

	private boolean isTripsTaskExecutedOnStart;

	@Override
	protected void attachTasks()
	{
		// No tasks are attached
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		addTask(TRIP_EXTRACTION_TASK_KEY,
				new TripExtractionTask(this, Long.valueOf(1)));
		executeTask(TRIP_EXTRACTION_TASK_KEY);
		isTripsTaskExecutedOnStart = true;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		final NotificationRemover remover = new NotificationRemover(this);
		remover.removeNotifications();
		final NotifiedIntentManager marker = NotifiedIntentManager
				.getInstance();
		if (marker.isMarked(getIntent()) && !isTripsTaskExecutedOnStart)
		{
			addTask(TRIP_EXTRACTION_TASK_KEY,
					new TripExtractionTask(this, Long.valueOf(1)));
			executeTask(TRIP_EXTRACTION_TASK_KEY);
		}

		isTripsTaskExecutedOnStart = false;
	}

	/**
	 * The method is called when the current user's trips are successfully
	 * extracted from the server
	 * 
	 */
	public void onSuccessfulTripsExtraction()
	{
		Toast.makeText(this, SUCCESSFUL_EXTRACTION, Toast.LENGTH_LONG).show();
	}

	/**
	 * The method is called when a problem occurs in the process of trips
	 * extraction
	 */
	public void onTripsExtractionProblem()
	{
		Toast.makeText(this, EXTRACTION_PROBLEM, Toast.LENGTH_LONG).show();
	}
}
