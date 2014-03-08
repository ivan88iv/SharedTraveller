package org.ai.shared.traveller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;

import org.ai.shared.traveller.announcement.AnnouncementsSwipeListFragment;
import org.ai.shared.traveller.announcement.input.InputAnnouncementFragment;
import org.ai.shared.traveller.command.request.INewRequestCommand;
import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.dialog.request.NewRequestDialog;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPutClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.shared.traveller.settings.SettingsActivity;
import org.ai.shared.traveller.task.AllCitiesTask;
import org.ai.shared.traveller.task.UserVehiclesTask;
import org.ai.shared.traveller.task.announcement.NewAnnouncementTask;
import org.ai.shared.traveller.task.request.NewRequestTask;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;
import org.codehaus.jackson.map.ObjectMapper;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.ITraveller;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;
import org.shared.traveller.client.domain.rest.Traveller;
import org.shared.traveller.client.domain.rest.event.NewRequestEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

public class MainActivity extends AbstractNetworkActivity implements
        ISaveAnnouncementCommand, INewRequestCommand, ICitiesProvider,
        IVehiclesProvider, ISimpleDialogListener
{
    private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
            "Could not submit the announcement {0}.";

    private static final String NEW_REQUEST_FAILED =
            "Could not send new request for {0}.";

    private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
            "newAnnouncementTask";

    private static final String NEW_REQUEST_TASK_KEY = "sendRequestTask";

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
        final SimpleClient getClient = new SimpleClient(RequestTypes.GET);
        addTask("DUMMY_TASK", new DummyTaskGet(this, getClient));
        executeTask("DUMMY_TASK");
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button showViewPagerIndicator = (Button) findViewById(R.id.show_view_pager_indicator);
        final Button showSwipeView = (Button) findViewById(R.id.show_swipe_list_view);
        final Button showNewRequestDialog = (Button) findViewById(
                R.id.show_new_request_dialog);

        final MainActivity activity = this;

        showViewPagerIndicator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                findViewById(R.id.fragment_container).setVisibility(
                        View.VISIBLE);
                final FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.add(R.id.fragment_container,
                        InputAnnouncementFragment
                                .newInstance(activity));
                fragmentTransaction.addToBackStack("viewPagerIndicator");
                fragmentTransaction.commit();

                showViewPagerIndicator.setVisibility(View.GONE);
                showSwipeView.setVisibility(View.GONE);
            }
        });

        showSwipeView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                findViewById(R.id.fragment_container).setVisibility(
                        View.VISIBLE);
                final FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.add(R.id.fragment_container,
                        new AnnouncementsSwipeListFragment(), "swipe");
                fragmentTransaction.addToBackStack("swipeListView");
                fragmentTransaction.commit();

                showViewPagerIndicator.setVisibility(View.GONE);
                showSwipeView.setVisibility(View.GONE);

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

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        findViewById(R.id.fragment_container).setVisibility(View.GONE);
        ((ViewGroup) findViewById(R.id.fragment_container)).removeAllViews();
        findViewById(R.id.show_view_pager_indicator)
                .setVisibility(View.VISIBLE);
        findViewById(R.id.show_swipe_list_view).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("aaa", "aaa");
    }

    @Override
    public void saveAnnouncement(final Announcement inAnnouncement)
    {
        final AbstractPutClient newAnnouncementClient =
                new AbstractPutClient()
                {
                    @Override
                    protected void submitData(final OutputStream inOutStream)
                            throws ServiceConnectionException
                    {
                        final ObjectMapper writer = new ObjectMapper();

                        try
                        {
                            writer.writeValue(inOutStream, inAnnouncement);
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
    }

    @Override
    public void sendRequest(final NewRequestEvent inEvent)
    {
        final AbstractPutClient sendRequestClient = new AbstractPutClient()
        {
            @Override
            protected void submitData(final OutputStream inOutStream)
                    throws ServiceConnectionException
            {
                final ObjectMapper writer = new ObjectMapper();

                try
                {
                    writer.writeValue(inOutStream, inEvent);
                } catch (final IOException ioe)
                {
                    final String errorMsg = MessageFormat.format(
                            NEW_REQUEST_FAILED, inEvent);

                    throw new ServiceConnectionException(errorMsg, ioe);
                }
            }
        };
        addTask(NEW_REQUEST_TASK_KEY, new NewRequestTask(this,
                sendRequestClient));
        executeTask(NEW_REQUEST_TASK_KEY);
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

    @Override
    public void onPositiveButtonClicked(final int requestCode)
    {
        if (requestCode == 0)
        {
            final ITraveller sender = new Traveller("temp");
            final IAnnouncement announcement =
                    new AnnouncementBuilder("Bansko", "Sofia",
                            new Date(114, 1, 9), (short) 5, "temp").build();
            final NewRequestEvent event = new NewRequestEvent(sender,
                    announcement);
            sendRequest(event);
        }
    }

    @Override
    public void onNegativeButtonClicked(final int requestCode)
    {
        // TODO Auto-generated method stub
    }
}
