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
import org.ai.shared.traveller.dialog.request.NewRequestDialog;
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
import org.ai.shared.traveller.task.request.NewRequestTask;
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
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

public class MainActivity extends AbstractNetworkActivity implements
		ISaveAnnouncementCommand, INewRequestCommand,
		ICitiesProvider, IVehiclesProvider, ISimpleDialogListener
{
	private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

	private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
			"Could not submit the announcement {0}.";


	private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
			"newAnnouncementTask";

	private static final String NEW_REQUEST_TASK_KEY = "sendRequestTask";

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
		final IServiceClient getClient = clientFactory.createSimpleClient(this,
				"dummy/asdadsad");
		addTask("DUMMY_TASK", new DummyTaskGet(this, getClient));
		executeTask("DUMMY_TASK");	}

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

		final MainActivity activity = this;

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
				Intent intent = new Intent(MainActivity.this,
						ShowAnnouncementsActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		showNewRequestDialog.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				NewRequestDialog.show(activity);
			}
		});

		showRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(activity,
						AnnouncementRequestActivity.class));
			}
		});
		showMyRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(activity,
						UserRequestsActivity.class));
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
		if (requestCode == 0)
		{
			final RequestInfoBuilder builder = new RequestInfoBuilder();
			builder.sender("temp")
					.fromPoint("Bansko").toPoint("Sofia")
					.departureDate(new Date(114, 1, 9))
					.departureDate(new Date())
					.driverUsername("temp");
			final RequestInfo request = builder.build();
			sendRequest(request);
		}
	}

	@Override
	public void onNegativeButtonClicked(final int requestCode)
	{
		// TODO Auto-generated method stub
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
