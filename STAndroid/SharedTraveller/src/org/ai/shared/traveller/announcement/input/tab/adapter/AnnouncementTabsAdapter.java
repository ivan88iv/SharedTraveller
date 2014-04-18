package org.ai.shared.traveller.announcement.input.tab.adapter;

import org.ai.shared.traveller.announcement.input.tab.PrimaryTab;
import org.ai.shared.traveller.announcement.input.tab.SecondaryTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AnnouncementTabsAdapter extends FragmentPagerAdapter
{
    private final PrimaryTab primaryTab;

    private final SecondaryTab secondaryTab;

    public AnnouncementTabsAdapter(final FragmentManager inManager)
    {
        super(inManager);

        primaryTab = PrimaryTab.newInstance(false, true, null);
        secondaryTab = new SecondaryTab();
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
            title = "Primary";
            break;
        case 1:
            title = "Secondary";
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
