package org.ai.shared.traveller.announcement.input;

import java.text.MessageFormat;

import org.ai.shared.traveller.announcement.input.tab.PrimaryTab;
import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.task.AllCitiesTask;
import org.ai.shared.traveller.task.UserVehiclesTask;
import org.ai.shared.traveller.task.announcement.NewAnnouncementTask;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.rest.Announcement;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class InputAnnouncementActivity extends AbstractNetworkActivity
		implements ISaveAnnouncementCommand, ICitiesProvider,
		IVehiclesProvider
{

	private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

	private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
			"Could not submit the announcement {0}.";

	private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
			"newAnnouncementTask";

	private final IServiceClientFactory clientFactory =
			DomainManager.getInstance().getServiceClientFactory();

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_fragment_container);

		configureActionBarForCustomView();
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container,
				PrimaryTab
						.newInstance(false, true, this), FRAGMENT_TAG);
		fragmentTransaction.commit();

	}

	private void configureActionBarForCustomView()
	{
		((ActionBarActivity) this).getSupportActionBar()
				.setDisplayOptions(
						ActionBar.DISPLAY_SHOW_CUSTOM,
						ActionBar.DISPLAY_HOME_AS_UP
								| ActionBar.DISPLAY_SHOW_HOME |
								ActionBar.DISPLAY_SHOW_TITLE
								| ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	@Override
	protected void attachTasks()
	{

	}

	@Override
	public void saveAnnouncement(final Announcement inAnnouncement)
	{
		final IServiceClient newAnnouncementClient =
				clientFactory.createNewResourceClient(
						InputAnnouncementActivity.this,
						"announcement/new", inAnnouncement,
						MessageFormat.format(UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT,
								inAnnouncement));
		addTask(CREATION_ANNOUNCEMNT_TASK_KEY, new NewAnnouncementTask(this,
				newAnnouncementClient));
		executeTask(CREATION_ANNOUNCEMNT_TASK_KEY);
		finish();
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
}
