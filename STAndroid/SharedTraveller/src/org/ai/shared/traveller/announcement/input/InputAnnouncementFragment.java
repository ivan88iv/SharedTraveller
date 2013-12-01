package org.ai.shared.traveller.announcement.input;

import org.ai.shared.traveller.announcement.builder.AnnouncementBuilder;
import org.ai.shared.traveller.announcement.input.save.ISaveAnnouncementCommand;
import org.ai.shared.traveller.announcement.input.tab.adapter.AnnouncementTabsAdapter;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.domain.Announcement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class InputAnnouncementFragment extends Fragment
{
    private AnnouncementTabsAdapter adapter;

    private ViewPager pager;

    private PageIndicator indicator;

    private ISaveAnnouncementCommand saveCommand;

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
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState)
    {
        final Context contextThemeWrapper = new ContextThemeWrapper(
                getActivity(), R.style.announcement_tab);
        final LayoutInflater themedInflater =
                inflater.cloneInContext(contextThemeWrapper);
        final View inputFragment = themedInflater.inflate(
                R.layout.input_announcement_fragment,
                container, false);

        adapter = new AnnouncementTabsAdapter(
                getActivity().getSupportFragmentManager());
        pager = (ViewPager) inputFragment.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        indicator = (TabPageIndicator) inputFragment
                .findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        return inputFragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        if (saveCommand != null && item.getItemId() == R.id.save_action)
        {
            final AnnouncementBuilder builder = new AnnouncementBuilder(
                    adapter.getPrimaryTab(), adapter.getSecondaryTab());
            final Announcement announcement = builder.build();
            saveCommand.saveAnnouncement(announcement);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater)
    {
        inflater.inflate(R.menu.save_action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
