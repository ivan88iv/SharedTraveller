package org.ai.shared.traveller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.ai.shared.traveller.announcement.AnnouncementsSwipeListFragment;
import org.ai.shared.traveller.announcement.input.InputAnnouncementFragment;
import org.ai.shared.traveller.announcement.input.NewAnnouncementTask;
import org.ai.shared.traveller.announcement.input.save.ISaveAnnouncementCommand;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPutClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.sharedtraveller.R;
import org.codehaus.jackson.map.ObjectMapper;
import org.shared.traveller.client.domain.rest.Announcement;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AbstractNetworkActivity implements
        ISaveAnnouncementCommand
{

    private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
            "Could not submit the announcement {0}.";

    private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
            "newAnnouncementTask";

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void attachTasks()
    {
        final SimpleClient sc = new SimpleClient(RequestTypes.GET);
        addTask("DUMMY_TASK", new DummyTaskGet(sc));
        executeTasks();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button showViewPagerIndicator = (Button) findViewById(R.id.show_view_pager_indicator);
        final Button showSwipeView = (Button) findViewById(R.id.show_swipe_list_view);

        final ISaveAnnouncementCommand saveAnnouncementListener = this;

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
                                .newInstance(saveAnnouncementListener));
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
        addTask(CREATION_ANNOUNCEMNT_TASK_KEY, new NewAnnouncementTask(
                newAnnouncementClient));
        executeTask(CREATION_ANNOUNCEMNT_TASK_KEY);
    }
}
