package org.ai.shared.traveller;

import java.text.MessageFormat;
import java.util.Date;

import org.ai.shared.traveller.announcement.activity.ShowAnnouncementsActivity;
import org.ai.shared.traveller.announcement.input.InputAnnouncementActivity;
import org.ai.shared.traveller.call.CallEnder;
import org.ai.shared.traveller.command.request.INewRequestCommand;
import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialogFragment;
import org.ai.shared.traveller.dialog.request.NewRequestDialog;
import org.ai.shared.traveller.dialog.request.RequestStatusNotificationFactory;
import org.ai.shared.traveller.dialog.trip.TripCancellationNotificationFactory;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.ai.shared.traveller.request.UserRequestsActivity;
import org.ai.shared.traveller.settings.SettingsActivity;
import org.ai.shared.traveller.task.AllCitiesTask;
import org.ai.shared.traveller.task.UserVehiclesTask;
import org.ai.shared.traveller.task.announcement.NewAnnouncementTask;
import org.ai.shared.traveller.task.request.DeclineRequestTask;
import org.ai.shared.traveller.task.request.NewRequestTask;
import org.ai.shared.traveller.task.trip.CancelTripTask;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.RequestInfo;
import org.shared.traveller.client.domain.rest.RequestInfo.RequestInfoBuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

public class MainActivity extends AbstractNetworkActivity implements
		ISaveAnnouncementCommand, INewRequestCommand,
		ICitiesProvider, IVehiclesProvider, ISimpleDialogListener
{
	private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
			"Could not submit the announcement {0}.";

	private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
			"newAnnouncementTask";

	private static final String NEW_REQUEST_TASK_KEY = "sendRequestTask";

	private static final String REQUEST_DECLINATION_TASK_KEY =
			"declineRequestTask";

	private static final String TRIP_CANCELLATION_TASK_KEY =
			"tripCancellation";

	private static final String SUCCESSFUL_REQUEST_DECLINATION =
			"The request was successfully declined.";

	private static final String REQUEST_DECLINATION_PROBLEM =
			"A problem occurred while trying to decline the request.";

	private static final String SUCCESSFUL_TRIP_CANCELLATION =
			"The trip was cancelled successfully.";

	private static final String TRIP_CANCELLATION_PROBLEM =
			"A problem occurred while trying to cancel the trip.";

	private final IServiceClientFactory clientFactory =
			DomainManager.getInstance().getServiceClientFactory();

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		if (R.id.action_settings == item.getItemId())
		{
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void attachTasks()
	{
		// No tasks are attached on activity startup
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		attachCallListener();

		final Button showViewPagerIndicator = (Button) findViewById(
				R.id.show_view_pager_indicator);
		final Button showSwipeView = (Button) findViewById(
				R.id.show_swipe_list_view);
		final Button showNewRequestDialog = (Button) findViewById(
				R.id.show_new_request_dialog);
		final Button showRequestsBtn = (Button) findViewById(
				R.id.show_requests);
		final Button showMyRequestsBtn = (Button) findViewById(
				R.id.show_my_requests);
		final Button requestDeclinationBtn = (Button) findViewById(
				R.id.send_request_declination_notification);
		final Button travelCancelationBtn = (Button) findViewById(
				R.id.travel_cancelation_notification);

		showViewPagerIndicator.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final Intent intent = new Intent(MainActivity.this,
						InputAnnouncementActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		showSwipeView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final Intent intent = new Intent(MainActivity.this,
						ShowAnnouncementsActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		showNewRequestDialog.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				NewRequestDialog.show(MainActivity.this);
			}
		});

		showRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(MainActivity.this,
						AnnouncementRequestActivity.class));
			}
		});

		showMyRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(MainActivity.this,
						UserRequestsActivity.class));
			}
		});

		requestDeclinationBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				STDialogFragment.show(new RequestStatusNotificationFactory(
						MainActivity.this, "request_declination_notification"));
			}
		});

		travelCancelationBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				STDialogFragment.show(new TripCancellationNotificationFactory(
						MainActivity.this, "travel_cancelation_notification"));
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	public void onBackPressed()
	{

	}

	@Override
	protected void onSaveInstanceState(final Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	@Override
	public void saveAnnouncement(final Announcement inAnnouncement)
	{
		final IServiceClient newAnnouncementClient =
				clientFactory.createNewResourceClient(MainActivity.this,
						"announcement/new", inAnnouncement,
						MessageFormat.format(UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT,
								inAnnouncement));
		addTask(CREATION_ANNOUNCEMNT_TASK_KEY, new NewAnnouncementTask(this,
				newAnnouncementClient));
		executeTask(CREATION_ANNOUNCEMNT_TASK_KEY);
	}

	@Override
	public void sendRequest(final RequestInfo inRequest)
	{
		addTask(NEW_REQUEST_TASK_KEY, new NewRequestTask(this, inRequest));
		executeTask(NEW_REQUEST_TASK_KEY);
	}

	@Override
	public void provideCityNames(final ICityComponentsPreparator inPreparator)
	{
		final IServiceClient getClient = clientFactory.createSimpleClient(this,
				"cities/all");
		final AllCitiesTask citiesTask = new AllCitiesTask(this, getClient,
				inPreparator);
		addTask("CITIES_TASK", citiesTask);
		executeTask("CITIES_TASK");
	}

	@Override
	public void provideVehicleNames(final String inUsername,
			final IVehicleComponentsPreparator inPreparator)
	{
		final IServiceClient getClient = clientFactory.createSimpleClient(this,
				"vehicles/" + inUsername);
		final UserVehiclesTask vehicleTask = new UserVehiclesTask(this,
				getClient, inPreparator);
		addTask("VEHICLE_TASK", vehicleTask);
		executeTask("VEHICLE_TASK");
	}

	@Override
	public void onPositiveButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.NEW_REQUEST.getCode())
		{
			final RequestInfoBuilder builder = new RequestInfoBuilder();
			builder.sender("temp")
					.fromPoint("Bansko").toPoint("Sofia")
					.departureDate(new Date(114, 1, 9))
					.driverUsername("temp");
			final RequestInfo request = builder.build();
			sendRequest(request);
		} else if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			// TODO replace the hard-coded request id
			addTask(REQUEST_DECLINATION_TASK_KEY, new DeclineRequestTask(
					this, Long.valueOf(31)));
			executeTask(REQUEST_DECLINATION_TASK_KEY);
		} else if (requestCode == DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION
				.getCode())
		{
			// TODO replace the hard-coded announcement id
			addTask(TRIP_CANCELLATION_TASK_KEY, new CancelTripTask(
					this, Long.valueOf(1)));
			executeTask(TRIP_CANCELLATION_TASK_KEY);
		}
	}

	@Override
	public void onNegativeButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION.getCode())
		{
			// TODO replace the hard-coded request id
			addTask(REQUEST_DECLINATION_TASK_KEY, new DeclineRequestTask(
					MainActivity.this, Long.valueOf(31)));
			executeTask(REQUEST_DECLINATION_TASK_KEY);
		} else if (requestCode == DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION
				.getCode())
		{
			// TODO replace the hard-coded announcement id
			addTask(TRIP_CANCELLATION_TASK_KEY, new CancelTripTask(
					this, Long.valueOf(1)));
			executeTask(TRIP_CANCELLATION_TASK_KEY);
		}
	}

	/**
	 * The method is called when a request has been declined successfully
	 */
	public void onSuccessfulRequestDeclination()
	{
		Toast.makeText(this, SUCCESSFUL_REQUEST_DECLINATION,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when a problem occurs in the process of a request
	 * declination
	 * 
	 */
	public void onRequestDeclinationProblem()
	{
		Toast.makeText(this, REQUEST_DECLINATION_PROBLEM, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * The method is called when a trip is successfully cancelled
	 */
	public void onSuccessfulTripCancellation()
	{
		Toast.makeText(this, SUCCESSFUL_TRIP_CANCELLATION,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when there was a problem while trying to cancel a
	 * trip
	 */
	public void onTripCancellationProblem()
	{
		Toast.makeText(this, TRIP_CANCELLATION_PROBLEM, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * The method attaches listeners to call made from the device
	 */
	private void attachCallListener()
	{
		final CallEnder callEnder = new CallEnder(this);
		final TelephonyManager callManager = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE);
		callManager.listen(callEnder, PhoneStateListener.LISTEN_CALL_STATE);
	}
}
