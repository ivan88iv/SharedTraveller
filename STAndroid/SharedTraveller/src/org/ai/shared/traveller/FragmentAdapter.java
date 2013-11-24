package org.ai.shared.traveller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter
        implements IconPagerAdapter
{
    public FragmentAdapter(final FragmentManager inManager)
    {
        super(inManager);
    }

    @Override
    public int getIconResId(final int index)
    {
        return 0;
    }

    @Override
    public Fragment getItem(final int position)
    {
        Fragment fragment = new FirstActivity();
        switch (position)
        {
        case 0:
            fragment = new FirstActivity();
            break;
        case 1:
            fragment = new SecondActivity();
            break;
        case 2:
            fragment = new ThirdActivity();
            break;
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(final int position)
    {
        String title = "";
        switch (position)
        {
        case 0:
            title = "First";
            break;
        case 1:
            title = "Second";
            break;
        case 2:
            title = "Third";
            break;
        }
        return title;
    }
}
