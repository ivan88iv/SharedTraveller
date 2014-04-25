package org.ai.shared.traveller.list.swipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * The class is used as a base class for the fragments that represent swipe list
 * view functionality
 * 
 * @author "Ivan Ivanov"
 * 
 * @param <T>
 *            the type of the elements represented in the swipe list
 * @param <A>
 *            the type of the adapter used to supplement elements for the
 *            fragment
 * 
 */
public abstract class AbstractSwipeListFragment<T, A extends BaseAdapter>
		extends Fragment
{

	private int swipeContainerLayoutId;

	private int swipeViewId;

	private A adapter;

	/**
	 * The method loads the id for the swipe container layout
	 * 
	 * @return the id of the swipe container layout
	 */
	protected abstract int loadSwipeContainerLayoutId();

	/**
	 * The method loads and returns the id of the swipe view
	 * 
	 * @return the id of the swipe view
	 */
	protected abstract int loadSwipeViewId();

	/**
	 * The method loads and returns the adapter used for the current fragment
	 * 
	 * @return the adapter used for the current fragment
	 */
	protected abstract A loadAdapter();

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
	 * Returns the adapter used for this fragment
	 * 
	 * @return the adapter that is used for the current fragment
	 */
	public A getAdapter()
	{
		return adapter;
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
