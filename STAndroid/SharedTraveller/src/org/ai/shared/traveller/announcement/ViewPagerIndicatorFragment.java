package org.ai.shared.traveller.announcement;

import org.ai.shared.traveller.FragmentAdapter;
import org.ai.sharedtraveller.R;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerIndicatorFragment extends Fragment
{

	private FragmentAdapter adapter;
	private ViewPager pager;
	private PageIndicator indicator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View fragment = inflater.inflate(R.layout.vire_pager_indicator_fragment, container, false);

		adapter = new FragmentAdapter(getActivity().getSupportFragmentManager());

		pager = (ViewPager) fragment.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TitlePageIndicator) fragment.findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		return fragment;
	}
}
