package org.ai.shared.traveller.announcement.fragment;

import org.ai.shared.traveller.announcement.adapter.AllAnnouncementsLazyLoadingAdapter;
import org.ai.shared.traveller.dialog.pickers.AbstractDatePickerDisplayer;
import org.ai.shared.traveller.dialog.pickers.AbstractPickerDisplayer;
import org.ai.shared.traveller.list.swipe.SettingsManager;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.param.SortOrder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * The fragment integrates swipe list view library.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AnnouncementsSwipeListFragment extends Fragment implements
		OnKeyListener
{
	/**
	 * Holder object for text views in list header.
	 * 
	 */
	static class HeaderViewHolder
	{
		EditText from;
		EditText to;
		EditText date;
		// Keeps sort order. May be it is not good idea to be kept here.
		SortOrder fromSortOrder = SortOrder.FROM_ASC;
		SortOrder toSortOrder = SortOrder.TO_ASC;
	}

	private SwipeListView swipeListView;

	private AllAnnouncementsLazyLoadingAdapter adapter;

	private HeaderViewHolder headerViewHolder;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// TODO correct the mistake here!
		adapter = null;
		// new AllAnnouncementsLazyLoadingAdapter(getActivity(),
		// new ArrayList<IAnnouncement>(),
		// new AnnouncementListHttpTask(getActivity()));
		headerViewHolder = new HeaderViewHolder();
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState)
	{
		final View fragment = inflater.inflate(
				R.layout.swipe_list_view_fragment, container, false);

		swipeListView = (SwipeListView) fragment
				.findViewById(R.id.swipe_list_view);
		final View header = View.inflate(getActivity(),
				R.layout.swipe_list_view_header, null);
		swipeListView.addHeaderView(header);
		configureHeader(header);

		swipeListView.setAdapter(adapter);

		if (savedInstanceState != null && savedInstanceState.containsKey("key"))
		{
			swipeListView.smoothScrollToPosition(savedInstanceState
					.getInt("key"));
		}

		reload();

		return fragment;

	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater)
	{
		inflater.inflate(R.menu.sort_order_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.sort_order_from:
			// Applies the sort order and changes current sort order.
			applySortOrder(headerViewHolder.fromSortOrder);
			if (headerViewHolder.fromSortOrder == SortOrder.FROM_ASC)
			{
				headerViewHolder.fromSortOrder = SortOrder.FROM_DESC;
			}
			else
			{
				headerViewHolder.fromSortOrder = SortOrder.FROM_ASC;
			}
			return true;
		case R.id.sort_order_to:
			// Applies the sort order and changes current sort order.
			applySortOrder(headerViewHolder.fromSortOrder);
			if (headerViewHolder.toSortOrder == SortOrder.TO_ASC)
			{
				headerViewHolder.toSortOrder = SortOrder.TO_DESC;
			}
			else
			{
				headerViewHolder.toSortOrder = SortOrder.TO_ASC;
			}
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void reload()
	{
		final SettingsManager settings = SettingsManager.getInstance();
		swipeListView.setSwipeMode(settings.getSwipeMode());
		swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
		swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
		swipeListView.setOffsetLeft(convertDpToPixel(settings
				.getSwipeOffsetLeft()));
		swipeListView.setOffsetRight(convertDpToPixel(settings
				.getSwipeOffsetRight()));
		swipeListView.setAnimationTime(settings.getSwipeAnimationTime());
		swipeListView
				.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
	}

	public int convertDpToPixel(final float dp)
	{
		final DisplayMetrics metrics = getResources().getDisplayMetrics();
		final float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	private void configureHeader(final View header)
	{
		headerViewHolder.from = (EditText) header
				.findViewById(R.id.header_from);
		headerViewHolder.to = (EditText) header.findViewById(R.id.header_to);
		headerViewHolder.date = (EditText) header
				.findViewById(R.id.header_date);

		headerViewHolder.from.setOnKeyListener(this);
		headerViewHolder.to.setOnKeyListener(this);

		final AbstractPickerDisplayer dateDisplayer = new AbstractDatePickerDisplayer()
		{
			@Override
			public void onDateSelecion(final DatePicker inView,
					final int inYear,
					final int inMonthOfYear,
					final int inDayOfMonth)
			{
				updateDisplay(inDayOfMonth, inMonthOfYear, inYear);
			}
		};
		dateDisplayer.display(getActivity().getSupportFragmentManager(),
				headerViewHolder.date);
	}

	@Override
	public boolean onKey(final View v, final int keyCode, final KeyEvent event)
	{
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (keyCode == KeyEvent.KEYCODE_ENTER))
		{
			applyFilters();
		}
		// Returning false allows other listeners to react to the press.
		return false;
	}

	private void hideKeyboard()
	{
		final InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void updateDisplay(final int day, final int month, final int year)
	{
		final StringBuilder builder = new StringBuilder();
		// Month is 0 based so add 1
		builder.append(day).append("-").append(month + 1).append("-")
				.append(year);
		headerViewHolder.date.setText(builder);
		applyFilters();
	}

	// Extract values from list view filters and changes the adapter. This
	// changed forces the list view to reload its data.
	private void applyFilters()
	{
		adapter = cerateAdapter(null);
		swipeListView.setAdapter(adapter);
		hideKeyboard();
	}

	private void applySortOrder(final SortOrder sortOrder)
	{
		adapter = cerateAdapter(sortOrder);
		swipeListView.setAdapter(adapter);
	}

	private AllAnnouncementsLazyLoadingAdapter cerateAdapter(
			final SortOrder sortOrder)
	{
		final String from = headerViewHolder.from.getText().toString();
		final String to = headerViewHolder.to.getText().toString();
		final String date = headerViewHolder.date.getText().toString();
		// return new AllAnnouncementsLazyLoadingAdapter(getActivity(),
		// new ArrayList<IAnnouncement>(), new AnnouncementListHttpTask(
		// getActivity(), from, to, date, sortOrder));
		// TODO correct the mistake here
		return null;
	}

}