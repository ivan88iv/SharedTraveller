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

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

	public InputAnnouncementFragment()
	{
		super();
	}

	public static InputAnnouncementFragment newInstance(
			final ISaveAnnouncementCommand inSaveCommand)
	{
		final InputAnnouncementFragment fragment =
				new InputAnnouncementFragment();
		fragment.saveCommand = inSaveCommand;
		return fragment;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState)
	{
		final Context contextThemeWrapper = new ContextThemeWrapper(
				getActivity(), R.style.announcement_tab);
		final LayoutInflater themedInflater =
				inflater.cloneInContext(contextThemeWrapper);
		final View inputFragment = themedInflater.inflate(
				R.layout.input_announcement_fragment,
				container, false);

		adapter = new AnnouncementTabsAdapter(
				getActivity().getSupportFragmentManager());
		pager = (ViewPager) inputFragment.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (TabPageIndicator) inputFragment
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		View actionBarButtons = inflater.inflate(
				R.layout.edit_event_custom_actionbar,
				container, false);
		configureActionBarCancelButton(actionBarButtons);
		configureActionBarDonelButton(actionBarButtons);
		((ActionBarActivity) getActivity()).getSupportActionBar()
				.setCustomView(actionBarButtons);

		return inputFragment;
	}

	private View configureActionBarCancelButton(View actionBarButtons)
	{
		View cancelActionView = actionBarButtons
				.findViewById(R.id.action_cancel);
		cancelActionView.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getActivity().finish();
			}
		});
		return cancelActionView;
	}

	private View configureActionBarDonelButton(View actionBarButtons)
	{
		View cancelActionView = actionBarButtons
				.findViewById(R.id.action_done);
		cancelActionView.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (saveCommand != null)
				{
					final PrimaryTab primaryTabInfo = adapter.getPrimaryTab();
					final SecondaryTab secondaryTabInfo = adapter
							.getSecondaryTab();
					final Context context = getActivity();
					final Resources resources = context.getResources();
					final IScreenValidator validator = new ScreenRequiredFieldValidator(
							context);
					final boolean isScreenCorrect = validator
							.validate(
									primaryTabInfo.getDepartureDate(),
									resources
											.getString(R.string.announcement_date))
							.validate(
									primaryTabInfo.getSeats(),
									resources
											.getString(R.string.announcement_seats))
							.validate(
									primaryTabInfo.getFromPoint(),
									resources
											.getString(R.string.announcement_start_pt))
							.validate(
									primaryTabInfo.getToPoint(),
									resources
											.getString(R.string.announcement_end_pt))
							.getResult();

					if (isScreenCorrect)
					{
						final Announcement announcement = buildNewAnnouncement(
								primaryTabInfo, secondaryTabInfo);
						saveCommand.saveAnnouncement(announcement);
					}
				}

			}
		});
		return cancelActionView;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		((ActionBarActivity) getActivity()).getSupportActionBar()
				.setCustomView(null);
	}

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
	}

	private Announcement buildNewAnnouncement(
			final PrimaryTab inPrimaryTabInfo,
			final SecondaryTab inSecondaryTabInfo)
	{
		// TODO THE USERNAME MUST COME FROM SOMEWHERE
		final AnnouncementBuilder builder = new AnnouncementBuilder(
				inPrimaryTabInfo.getFromPoint(),
				inPrimaryTabInfo.getToPoint(),
				inPrimaryTabInfo.getDepartureDate(),
				inPrimaryTabInfo.getSeats(),
				"temp");
		builder.depAddress(inSecondaryTabInfo.getDepartureAddress())
				.depTime(inPrimaryTabInfo.getDepartureTime())
				.price(inPrimaryTabInfo.getPrice())
				.vehicleName(inSecondaryTabInfo.getSelectedVehicleName());
		List<String> intermediatePoints = null;

		final Map<String, Spinner> intermediateSpinners = inSecondaryTabInfo
				.getIntermediatePtsSpinners();
		if (null != intermediateSpinners)
		{
			intermediatePoints = new ArrayList<String>();
			for (final Map.Entry<String, Spinner> intermEntry : intermediateSpinners
					.entrySet())
			{
				final Spinner currentSpinner = intermEntry.getValue();
				intermediatePoints.add(currentSpinner.getSelectedItem()
						.toString());
			}
		}

		return builder.intermediatePoints(intermediatePoints).build();
	}

}
