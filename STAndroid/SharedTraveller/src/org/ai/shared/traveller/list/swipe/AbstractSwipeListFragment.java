package org.ai.shared.traveller.list.swipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fortysevendeg.swipelistview.SwipeListView;

public abstract class AbstractSwipeListFragment<T> extends Fragment
{
	private int swipeContainerLayoutId;

	private int swipeViewId;

	private BaseAdapter adapter;

	protected abstract int loadSwipeContainerLayoutId();

	protected abstract int loadSwipeViewId();

	protected abstract BaseAdapter loadAdapter();

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		swipeContainerLayoutId = loadSwipeContainerLayoutId();
		swipeViewId = loadSwipeViewId();
		adapter = loadAdapter();
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container,
			final Bundle savedInstanceState)
	{
		final View swipeContainer = inflater.inflate(
				swipeContainerLayoutId, container, false);
		final SwipeListView swipeView =
				(SwipeListView) swipeContainer.findViewById(swipeViewId);
		swipeView.setAdapter(adapter);
		prepareSwipeSettings(swipeView);

		return swipeContainer;
	}

	/**
	 * The method prepares the swipe settings for the swipe list view
	 * 
	 * @param inSwipeView
	 *            the swipe list view which settings are prepared
	 */
	private void prepareSwipeSettings(
			final SwipeListView inSwipeView)
	{
		final SettingsManager settings = SettingsManager.getInstance();
		inSwipeView.setSwipeMode(settings.getSwipeMode());
		inSwipeView.setSwipeActionLeft(settings.getSwipeActionLeft());
		inSwipeView.setSwipeActionRight(settings.getSwipeActionRight());
		inSwipeView.setOffsetLeft(convertDpToPixel(settings
				.getSwipeOffsetLeft()));
		inSwipeView.setOffsetRight(convertDpToPixel(settings
				.getSwipeOffsetRight()));
		inSwipeView.setAnimationTime(settings.getSwipeAnimationTime());
		inSwipeView
				.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
	}

	/**
	 * The method converts dp units to pixels
	 * 
	 * @param dp
	 *            the dp unit to be converted
	 * @return the converted value in pixels
	 */
	private int convertDpToPixel(final float dp)
	{
		final DisplayMetrics metrics = getResources().getDisplayMetrics();
		final float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}
}
