package org.ai.shared.traveller.announcement.input;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPutClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.shared.traveller.task.AllCitiesTask;
import org.ai.shared.traveller.task.UserVehiclesTask;
import org.ai.shared.traveller.task.announcement.NewAnnouncementTask;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;
import org.codehaus.jackson.map.ObjectMapper;
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
				InputAnnouncementFragment
						.newInstance(this), FRAGMENT_TAG);
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
		final AbstractPutClient newAnnouncementClient =
				new AbstractPutClient()
				{
					@Override
					protected void submitData(final OutputStream inStream)
							throws ServiceConnectionException
					{
						final ObjectMapper writer = new ObjectMapper();

						try
						{
							writer.writeValue(inStream, inAnnouncement);
						} catch (final IOException ioe)
						{
							final String errorMsg = MessageFormat.format(
									UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT,
									inAnnouncement);

							throw new ServiceConnectionException(
									errorMsg, ioe);
						}
					}
				};
		addTask(CREATION_ANNOUNCEMNT_TASK_KEY, new NewAnnouncementTask(this,
				newAnnouncementClient));
		executeTask(CREATION_ANNOUNCEMNT_TASK_KEY);
		finish();
	}

	@Override
	public void provideCityNames(final ICityComponentsPreparator inPreparator)
	{
		final SimpleClient getClient = new SimpleClient(RequestTypes.GET);
		final AllCitiesTask citiesTask = new AllCitiesTask(this, getClient,
				inPreparator);
		addTask("CITIES_TASK", citiesTask);
		executeTask("CITIES_TASK");
	}

	@Override
	public void provideVehicleNames(final String inUsername,
			final IVehicleComponentsPreparator inPreparator)
	{
		final SimpleClient getClient = new SimpleClient(RequestTypes.GET);
		final UserVehiclesTask vehicleTask = new UserVehiclesTask(this,
				getClient, inUsername, inPreparator);
		addTask("VEHICLE_TASK", vehicleTask);
		executeTask("VEHICLE_TASK");
	}

}
