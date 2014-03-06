package org.ai.shared.traveller.announcement.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ai.shared.traveller.announcement.input.tab.PrimaryTab;
import org.ai.shared.traveller.announcement.input.tab.SecondaryTab;
import org.ai.shared.traveller.announcement.input.tab.adapter.AnnouncementTabsAdapter;
import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.validator.IScreenValidator;
import org.ai.shared.traveller.validator.required.ScreenRequiredFieldValidator;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class InputAnnouncementFragment extends Fragment
{
	private AnnouncementTabsAdapter adapter;

	private ViewPager pager;

	private PageIndicator indicator;

	private ISaveAnnouncementCommand saveCommand;

	private ActionBarActivity activity;

	public InputAnnouncementFragment()
	{
		super();
	}

	public static InputAnnouncementFragment newInstance(final ISaveAnnouncementCommand inSaveCommand)
	{
		final InputAnnouncementFragment fragment = new InputAnnouncementFragment();
		fragment.saveCommand = inSaveCommand;
		return fragment;
	}

	@Override
	public void onAttach(final Activity inActivity)
	{
		super.onAttach(inActivity);
		activity = (ActionBarActivity) inActivity;
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		menu.clear();
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
	{
		final Context contextThemeWrapper = new ContextThemeWrapper(activity, R.style.announcement_tab);
		final LayoutInflater themedInflater = inflater.cloneInContext(contextThemeWrapper);
		final View inputFragment = themedInflater.inflate(R.layout.input_announcement_fragment, container, false);

		adapter = new AnnouncementTabsAdapter(activity.getSupportFragmentManager(), activity.getResources());
		pager = (ViewPager) inputFragment.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) inputFragment.findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		showDoneBar();

		return inputFragment;
	}

	private Announcement buildNewAnnouncement(final PrimaryTab inPrimaryTabInfo, final SecondaryTab inSecondaryTabInfo)
	{
		// TODO THE USERNAME MUST COME FROM SOMEWHERE
		final AnnouncementBuilder builder =
				new AnnouncementBuilder(inPrimaryTabInfo.getFromPoint(), inPrimaryTabInfo.getToPoint(), inPrimaryTabInfo.getDepartureDate(), inPrimaryTabInfo.getSeats(), "temp");
		builder.depAddress(inSecondaryTabInfo.getDepartureAddress()).depTime(inPrimaryTabInfo.getDepartureTime()).price(inPrimaryTabInfo.getPrice())
				.vehicleName(inSecondaryTabInfo.getSelectedVehicleName());
		List<String> intermediatePoints = null;

		final Map<String, Spinner> intermediateSpinners = inSecondaryTabInfo.getIntermediatePtsSpinners();
		if (null != intermediateSpinners)
		{
			intermediatePoints = new ArrayList<String>();
			for (final Map.Entry<String, Spinner> intermEntry : intermediateSpinners.entrySet())
			{
				final Spinner currentSpinner = intermEntry.getValue();
				intermediatePoints.add(currentSpinner.getSelectedItem().toString());
			}
		}

		return builder.intermediatePoints(intermediatePoints).build();
	}

	private void showDoneBar()
	{
		// BEGIN_INCLUDE (inflate_set_custom_view)
		// Inflate a "Done/Cancel" custom action bar view.
		final LayoutInflater inflater = (LayoutInflater) activity.getSupportActionBar().getThemedContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(R.layout.actionbar_custom_view_done_cancel, null);
		customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// "Done"
				final PrimaryTab primaryTabInfo = adapter.getPrimaryTab();
				final SecondaryTab secondaryTabInfo = adapter.getSecondaryTab();
				final Resources resources = activity.getResources();
				final IScreenValidator validator = new ScreenRequiredFieldValidator(activity);
				final boolean isScreenValidated =
						validator.validate(primaryTabInfo.getDepartureDate(), resources.getString(R.string.announcement_date))
								.validate(primaryTabInfo.getSeats(), resources.getString(R.string.announcement_seats))
								.validate(primaryTabInfo.getFromPoint(), resources.getString(R.string.announcement_start_pt))
								.validate(primaryTabInfo.getToPoint(), resources.getString(R.string.announcement_end_pt)).getResult();

				if (isScreenValidated)
				{
					final Announcement announcement = buildNewAnnouncement(primaryTabInfo, secondaryTabInfo);
					saveCommand.saveAnnouncement(announcement);
				}

			}
		});
		customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// "Cancel"
				activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
				activity.getSupportFragmentManager().popBackStack();
			}
		});

		// Show the custom action bar view and hide the normal Home icon and
		// title.
		final ActionBar actionBar = activity.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		// END_INCLUDE (inflate_set_custom_view)
	}

}
