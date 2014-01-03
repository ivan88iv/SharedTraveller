package org.ai.shared.traveller.announcement.input.tab.adapter;

import org.ai.shared.traveller.announcement.input.tab.PrimaryTab;
import org.ai.shared.traveller.announcement.input.tab.SecondaryTab;
import org.ai.sharedtraveller.R;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AnnouncementTabsAdapter extends FragmentPagerAdapter
{
    private static final String NULL_RESOURCES = "The resources may not be null.";

    private static final String TWO_TABS = "There must be exactly two tab labels.";

    private final PrimaryTab primaryTab;

    private final SecondaryTab secondaryTab;

    private final String[] tabTitles;

    public AnnouncementTabsAdapter(final FragmentManager inManager,
            final Resources inResources)
    {
        super(inManager);

        assert null != inResources : NULL_RESOURCES;

        primaryTab = PrimaryTab.newInstance(false, true);
        secondaryTab = new SecondaryTab();
        tabTitles = inResources
                .getStringArray(R.array.announcement_tab_titles);

        assert tabTitles != null && tabTitles.length == 2 : TWO_TABS;
    }

    @Override
    public Fragment getItem(final int position)
    {
        Fragment fragment = null;

        switch (position)
        {
        case 0:
            fragment = primaryTab;
            break;
        case 1:
            fragment = secondaryTab;
            break;
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position)
    {
        String title = "";
        switch (position)
        {
        case 0:
            title = tabTitles[0];
            break;
        case 1:
            title = tabTitles[1];
            break;
        }
        return title;
    }

    public PrimaryTab getPrimaryTab()
    {
        return primaryTab;
    }

    public SecondaryTab getSecondaryTab()
    {
        return secondaryTab;
    }
}
