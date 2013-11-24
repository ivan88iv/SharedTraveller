package org.ai.shared.traveller.announcement;

import java.util.ArrayList;

import org.ai.shared.traveller.announcement.adapter.AnnouncementLazyLoadingAdapter;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.domain.Announcement;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

public class AnnouncementsSwipeListFragment extends Fragment
{

	private SwipeListView swipeListView;

	private AnnouncementLazyLoadingAdapter adapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		adapter = new AnnouncementLazyLoadingAdapter(getActivity(), new ArrayList<Announcement>());

		View fragment = inflater.inflate(R.layout.swipe_list_view_fragment, container, false);

		swipeListView = (SwipeListView) fragment.findViewById(R.id.example_lv_list);

		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener()
		{
			@Override
			public void onOpened(int position, boolean toRight)
			{
			}

			@Override
			public void onClosed(int position, boolean fromRight)
			{
			}

			@Override
			public void onListChanged()
			{
			}

			@Override
			public void onMove(int position, float x)
			{
			}

			@Override
			public void onStartOpen(int position, int action, boolean right)
			{
				Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
			}

			@Override
			public void onStartClose(int position, boolean right)
			{
				Log.d("swipe", String.format("onStartClose %d", position));
			}

			@Override
			public void onClickFrontView(int position)
			{
				Log.d("swipe", String.format("onClickFrontView %d", position));
			}

			@Override
			public void onClickBackView(int position)
			{
				Log.d("swipe", String.format("onClickBackView %d", position));
			}

		});

		swipeListView.setAdapter(adapter);

		reload();

		return fragment;

	}

	private void reload()
	{
		SettingsManager settings = SettingsManager.getInstance();
		swipeListView.setSwipeMode(settings.getSwipeMode());
		swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
		swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
		swipeListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
		swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
		swipeListView.setAnimationTime(settings.getSwipeAnimationTime());
		swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
	}

	public int convertDpToPixel(float dp)
	{
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

}
