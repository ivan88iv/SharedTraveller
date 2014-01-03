package org.ai.shared.traveller.announcement.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ai.shared.traveller.announcement.input.save.ISaveAnnouncementCommand;
import org.ai.shared.traveller.announcement.input.tab.PrimaryTab;
import org.ai.shared.traveller.announcement.input.tab.SecondaryTab;
import org.ai.shared.traveller.announcement.input.tab.adapter.AnnouncementTabsAdapter;
import org.ai.shared.traveller.validator.IScreenValidator;
import org.ai.shared.traveller.validator.required.ScreenRequiredFieldValidator;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class InputAnnouncementFragment extends Fragment
{
    private AnnouncementTabsAdapter adapter;

    private ViewPager pager;

    private PageIndicator indicator;

    private ISaveAnnouncementCommand saveCommand;

    private ActionMode actionMode;

    private ActionBarActivity activity;

    public InputAnnouncementFragment()
    {
        super();
    }

    public static InputAnnouncementFragment newInstance(
            final ISaveAnnouncementCommand inSaveCommand)
    {
        final InputAnnouncementFragment fragment =
                new InputAnnouncementFragment();
        fragment.saveCommand = inSaveCommand;
        return fragment;
    }

    @Override
    public void onAttach(final Activity inActivity)
    {
        activity = (ActionBarActivity) inActivity;
        super.onAttach(inActivity);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState)
    {
        final Context contextThemeWrapper = new ContextThemeWrapper(
                activity, R.style.announcement_tab);
        final LayoutInflater themedInflater =
                inflater.cloneInContext(contextThemeWrapper);
        final View inputFragment = themedInflater.inflate(
                R.layout.input_announcement_fragment,
                container, false);

        adapter = new AnnouncementTabsAdapter(
                activity.getSupportFragmentManager(),
                activity.getResources());
        pager = (ViewPager) inputFragment.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        indicator = (TabPageIndicator) inputFragment
                .findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        startActionMode();

        return inputFragment;
    }

    private Announcement buildNewAnnouncement(
            final PrimaryTab inPrimaryTabInfo,
            final SecondaryTab inSecondaryTabInfo)
    {
        // TODO THE USERNAME MUST COME FROM SOMEWHERE
        final AnnouncementBuilder builder = new AnnouncementBuilder(
                inPrimaryTabInfo.getFromPoint(),
                inPrimaryTabInfo.getToPoint(),
                inPrimaryTabInfo.getDepartureDate(),
                inPrimaryTabInfo.getSeats(),
                "temp");
        builder.depAddress(inSecondaryTabInfo.getDepartureAddress())
                .depTime(inPrimaryTabInfo.getDepartureTime())
                .price(inPrimaryTabInfo.getPrice())
                .vehicleName(inSecondaryTabInfo.getSelectedVehicleName());
        List<String> intermediatePoints = null;

        final Map<String, Spinner> intermediateSpinners = inSecondaryTabInfo
                .getIntermediatePtsSpinners();
        if (null != intermediateSpinners)
        {
            intermediatePoints = new ArrayList<String>();
            for (final Map.Entry<String, Spinner> intermEntry : intermediateSpinners
                    .entrySet())
            {
                final Spinner currentSpinner = intermEntry.getValue();
                intermediatePoints.add(currentSpinner.getSelectedItem()
                        .toString());
            }
        }

        return builder.intermediatePoints(intermediatePoints).build();
    }

    private void startActionMode()
    {
        if (actionMode == null)
        {
            final ActionMode.Callback actionModeCallback = prepareActionModeCallback();
            actionMode = activity.startSupportActionMode(actionModeCallback);
        }
    }

    private ActionMode.Callback prepareActionModeCallback()
    {
        return new ActionMode.Callback()
        {
            @Override
            public boolean onPrepareActionMode(final ActionMode inMode,
                    final Menu inMenu)
            {
                return false;
            }

            @Override
            public void onDestroyActionMode(final ActionMode inMode)
            {
                actionMode = null;
            }

            @Override
            public boolean onCreateActionMode(final ActionMode inMode,
                    final Menu inMenu)
            {
                final MenuInflater inflater = inMode.getMenuInflater();
                inflater.inflate(R.menu.save_action_menu, inMenu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode inMode,
                    final MenuItem inItem)
            {
                if (saveCommand != null
                        && inItem.getItemId() == R.id.save_action)
                {
                    final PrimaryTab primaryTabInfo = adapter.getPrimaryTab();
                    final SecondaryTab secondaryTabInfo = adapter
                            .getSecondaryTab();
                    final Resources resources = activity.getResources();
                    final IScreenValidator validator = new ScreenRequiredFieldValidator(
                            activity);
                    final boolean isScreenValidated = validator
                            .validate(
                                    primaryTabInfo.getDepartureDate(),
                                    resources
                                            .getString(R.string.announcement_date))
                            .validate(
                                    primaryTabInfo.getSeats(),
                                    resources
                                            .getString(R.string.announcement_seats))
                            .validate(
                                    primaryTabInfo.getFromPoint(),
                                    resources
                                            .getString(R.string.announcement_start_pt))
                            .validate(
                                    primaryTabInfo.getToPoint(),
                                    resources
                                            .getString(R.string.announcement_end_pt))
                            .getResult();

                    if (isScreenValidated)
                    {
                        final Announcement announcement = buildNewAnnouncement(
                                primaryTabInfo, secondaryTabInfo);
                        saveCommand.saveAnnouncement(announcement);
                    }

                    return true;
                }

                return false;
            }
        };
    }
}
