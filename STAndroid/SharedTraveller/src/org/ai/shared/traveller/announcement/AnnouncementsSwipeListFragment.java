package org.ai.shared.traveller.announcement;

import java.util.ArrayList;

import org.ai.shared.traveller.announcement.adapter.LazyLoadingAdapter;
import org.ai.shared.traveller.announcement.adapter.http.AnnouncementListHttpTask;
import org.ai.shared.traveller.announcement.date.DatePickerFragment;
import org.ai.shared.traveller.announcement.date.IOnDateSetListener;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.domain.Announcement;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * The fragment integrates swipe list view library.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AnnouncementsSwipeListFragment extends Fragment implements OnKeyListener
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
	}

	private SwipeListView swipeListView;

	private LazyLoadingAdapter adapter;

	private HeaderViewHolder headerViewHolder;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		adapter = new LazyLoadingAdapter(getActivity(), new ArrayList<Announcement>(), new AnnouncementListHttpTask());
		headerViewHolder = new HeaderViewHolder();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View fragment = inflater.inflate(R.layout.swipe_list_view_fragment, container, false);

		swipeListView = (SwipeListView) fragment.findViewById(R.id.example_lv_list);
		View header = View.inflate(getActivity(), R.layout.swipe_list_view_header, null);
		swipeListView.addHeaderView(header);
		configureHeader(header);

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

		if (savedInstanceState != null && savedInstanceState.containsKey("key"))
		{
			swipeListView.smoothScrollToPosition(savedInstanceState.getInt("key"));
		}

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

	private void configureHeader(View header)
	{
		headerViewHolder.from = (EditText) header.findViewById(R.id.header_from);
		headerViewHolder.to = (EditText) header.findViewById(R.id.header_to);
		headerViewHolder.date = (EditText) header.findViewById(R.id.header_date);

		headerViewHolder.from.setOnKeyListener(this);
		headerViewHolder.to.setOnKeyListener(this);

		// When date EditText gets the focus the date picker is displayed.
		headerViewHolder.date.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
				{
					DialogFragment newFragment = new DatePickerFragment();
					Bundle bundle = new Bundle();
					bundle.putSerializable(DatePickerFragment.DATE_PICKER_LISTENER, new IOnDateSetListener()
					{

						private static final long serialVersionUID = 1L;

						@Override
						public void onDateSet(DatePicker view, int year, int month, int day)
						{
							updateDisplay(day, month, year);
						}
					});
					newFragment.setArguments(bundle);
					newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
				}
			}

		});
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event)
	{
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
		{
			applyFilters();
		}
		// Returning false allows other listeners to react to the press.
		return false;
	}

	private void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void updateDisplay(int day, int month, int year)
	{
		StringBuilder builder = new StringBuilder();
		// Month is 0 based so add 1
		builder.append(day).append("-").append(month + 1).append("-").append(year);
		headerViewHolder.date.setText(builder);
		applyFilters();
	}

	// Extract values from list view filters and changes the adapter. This
	// changed forces the list view to reload its data.
	private void applyFilters()
	{
		String from = headerViewHolder.from.getText().toString();
		String to = headerViewHolder.to.getText().toString();
		String date = headerViewHolder.date.getText().toString();
		adapter = new LazyLoadingAdapter(getActivity(), new ArrayList<Announcement>(), new AnnouncementListHttpTask(from, to, date));
		swipeListView.setAdapter(adapter);
		hideKeyboard();
	}

}
