package org.ai.shared.traveller.announcement.input.tab;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.content.converter.ContentConverter;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.dialog.pickers.AbstractDatePickerDisplayer;
import org.ai.shared.traveller.dialog.pickers.AbstractPickerDisplayer;
import org.ai.shared.traveller.dialog.pickers.AbstractTimePickerDisplayer;
import org.ai.shared.traveller.msg.reader.LocalizedMsgReader;
import org.ai.shared.traveller.spinner.HintSpinnerAdapter;
import org.ai.shared.traveller.spinner.SpinnerItem;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
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
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

public class PrimaryTab extends Fragment
{

	private static int intermediatePtsCnt = 0;

	private ICitiesProvider citiesProvider;

	private IVehiclesProvider vehiclesProvider;

	private String selectedVehicle;

	private EditText departureAddress;

	private final Map<String, Spinner> intermediatePtsSpinners = new
			HashMap<String, Spinner>();

	private boolean disablePrimaryFields;

	private boolean showRequiredFields;

	private String fromPoint;

	private String toPoint;

	private Calendar departureDate;

	private Calendar depeartureTime;

	private EditText priceHolder;

	private EditText seats;

	private ISaveAnnouncementCommand saveCommand;

	public PrimaryTab()
	{
		super();
	}

	@Override
	public void onAttach(final Activity activity)
	{
		try
		{
			citiesProvider = (ICitiesProvider) activity;
		} catch (final ClassCastException cce)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement the ICitiesProvider interface.");
		}

		try
		{
			vehiclesProvider = (IVehiclesProvider) activity;
		} catch (final ClassCastException cce)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement the IVehiclesProvider interface.");
		}

		super.onAttach(activity);
	}

	public static PrimaryTab newInstance(final boolean inDisablePrimaryFields,
			final boolean inShowRequiredFields,
			final ISaveAnnouncementCommand inSaveCommand)
	{
		final PrimaryTab primaryTab = new PrimaryTab();
		primaryTab.disablePrimaryFields = inDisablePrimaryFields;
		primaryTab.showRequiredFields = inShowRequiredFields;
		primaryTab.saveCommand = inSaveCommand;

		return primaryTab;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState)
	{
		final View primaryTab = inflater.inflate(
				R.layout.announcement_primary_tab, null);
		loadViews(primaryTab);

		if (disablePrimaryFields)
		{
			disablePrimaryFields(primaryTab);
		}

		if (!showRequiredFields)
		{
			hideRequiredFields(primaryTab);
		}

		final IVehicleComponentsPreparator preparator = new IVehicleComponentsPreparator()
		{
			@Override
			public void prepareComponents(String[] inVehicleNames)
			{
				if (inVehicleNames.length == 0)
				{
					inVehicleNames = new String[]
					{ "No registred vehicles" };
				}
				final ArrayAdapter<CharSequence> vehiclesAdapter =
						new ArrayAdapter<CharSequence>(getActivity(),
								android.R.layout.simple_spinner_item,
								inVehicleNames);
				vehiclesAdapter.setDropDownViewResource(
						android.R.layout.simple_spinner_dropdown_item);
				prepareVehiclesSpinner(primaryTab, vehiclesAdapter);
			}

		};
		// TODO the username must come from somewhere
		vehiclesProvider.provideVehicleNames("temp", preparator);

		final TextView newIntermediatePtBtn = (TextView) primaryTab
				.findViewById(
				R.id.new_intermediate_pt_btn);
		newIntermediatePtBtn.setOnClickListener(
				new IntermediatePointInserter());

		departureAddress = (EditText) primaryTab
				.findViewById(R.id.departure_address);

		View actionBarButtons = inflater.inflate(
				R.layout.edit_event_custom_actionbar,
				container, false);
		configureActionBarCancelButton(actionBarButtons);
		configureActionBarDonelButton(actionBarButtons);
		((ActionBarActivity) getActivity()).getSupportActionBar()
				.setCustomView(actionBarButtons);

		return primaryTab;
	}

	public String getSelectedVehicleName()
	{
		return selectedVehicle;
	}

	public String getDepartureAddress()
	{
		return ContentConverter.toString(departureAddress);
	}

	public Map<String, Spinner> getIntermediatePtsSpinners()
	{
		return Collections.unmodifiableMap(intermediatePtsSpinners);
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
					final Context context = getActivity();
					final Resources resources = context.getResources();
					final IScreenValidator validator = new ScreenRequiredFieldValidator(
							context);
					final boolean isScreenCorrect = validator
							.validate(
									getDepartureDate(),
									resources
											.getString(R.string.announcement_date))
							.validate(
									getSeats(),
									resources
											.getString(R.string.announcement_seats))
							.validate(
									getFromPoint(),
									resources
											.getString(R.string.announcement_start_pt))
							.validate(
									getToPoint(),
									resources
											.getString(R.string.announcement_end_pt))
							.getResult();

					if (isScreenCorrect)
					{
						final Announcement announcement = buildNewAnnouncement(
								PrimaryTab.this);
						saveCommand.saveAnnouncement(announcement);
					}
				}

			}
		});
		return cancelActionView;
	}

	private Announcement buildNewAnnouncement(
			final PrimaryTab inPrimaryTabInfo)
	{
		// TODO THE USERNAME MUST COME FROM SOMEWHERE
		final AnnouncementBuilder builder = new AnnouncementBuilder(
				inPrimaryTabInfo.getFromPoint(),
				inPrimaryTabInfo.getToPoint(),
				inPrimaryTabInfo.getDepartureDate(),
				inPrimaryTabInfo.getSeats(),
				"temp");
		builder.depAddress(inPrimaryTabInfo.getDepartureAddress())
				.depTime(inPrimaryTabInfo.getDepartureTime())
				.price(inPrimaryTabInfo.getPrice())
				.vehicleName(inPrimaryTabInfo.getSelectedVehicleName());
		List<String> intermediatePoints = null;

		final Map<String, Spinner> intermediateSpinners = inPrimaryTabInfo
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

	public String getFromPoint()
	{
		return fromPoint;
	}

	public String getToPoint()
	{
		return toPoint;
	}

	public Date getDepartureDate()
	{
		return getTime(departureDate);
	}

	public Date getDepartureTime()
	{
		return getTime(depeartureTime);
	}

	public BigDecimal getPrice()
	{
		return ContentConverter.toBigDecimal(priceHolder);
	}

	public Short getSeats()
	{
		return ContentConverter.toShort(seats);
	}

	private void loadViews(final View inPrimaryTab)
	{
		final ICityComponentsPreparator preparator = new ICityComponentsPreparator()
		{
			@Override
			public void prepareComponents(final String[] inCityNames)
			{
				prepareStartSpinner(inPrimaryTab, inCityNames);
				prepareEndSpinner(inPrimaryTab, inCityNames);
			}

		};
		citiesProvider.provideCityNames(preparator);

		final EditText dateHolder = (EditText) inPrimaryTab
				.findViewById(R.id.announcement_date);
		final AbstractPickerDisplayer dateDisplayer = new AbstractDatePickerDisplayer()
		{
			@Override
			public void onDateSelecion(final DatePicker view, final int inYear,
					final int inMonthOfYear,
					final int inDayOfMonth)
			{
				updateDate(dateHolder, inYear, inMonthOfYear, inDayOfMonth);
			}
		};
		dateDisplayer.display(getActivity().getSupportFragmentManager(),
				dateHolder);

		final EditText timeHolder = (EditText) inPrimaryTab
				.findViewById(R.id.announcement_time);
		final AbstractPickerDisplayer timeDisplayer = new AbstractTimePickerDisplayer()
		{
			@Override
			public void onTimeSelection(final TimePicker view,
					final int inHourOfDay,
					final int inMinute)
			{
				updateTime(timeHolder, inHourOfDay, inMinute);
			}
		};
		timeDisplayer.display(getActivity().getSupportFragmentManager(),
				timeHolder);

		priceHolder = (EditText) inPrimaryTab.findViewById(
				R.id.announcement_price);
		seats = (EditText) inPrimaryTab.findViewById(R.id.announcement_seats);

	}

	private HintSpinnerAdapter prepareIntermediatePointAdapter(
			final String[] inCityNames)
	{
		List<SpinnerItem> spinnerItems = new ArrayList<SpinnerItem>();
		createSpinnerItems(spinnerItems, inCityNames);
		spinnerItems.add(getIntermediatePointHintSpinnerItem());
		final HintSpinnerAdapter hintAdapter = new HintSpinnerAdapter(
				getActivity(),
				android.R.layout.simple_spinner_item, spinnerItems);
		hintAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		return hintAdapter;
	}

	private List<SpinnerItem> createSpinnerItems(
			final List<SpinnerItem> inSpinnerItems, final String[] inCityNames)
	{
		for (String cityName : inCityNames)
		{
			SpinnerItem item = new SpinnerItem(cityName, false);
			inSpinnerItems.add(item);
		}
		return inSpinnerItems;
	}

	private SpinnerItem getIntermediatePointHintSpinnerItem()
	{
		return new SpinnerItem(getActivity().getString(
				R.string.announcement_intermediate_pt), true);
	}

	private void updateTime(final EditText inTimeHolder,
			final int inHourOfDay, final int inMinute)
	{
		final LocalizedMsgReader msgReader = new LocalizedMsgReader(
				getActivity());
		depeartureTime = Calendar.getInstance();
		depeartureTime.set(Calendar.HOUR_OF_DAY, inHourOfDay);
		depeartureTime.set(Calendar.MINUTE, inMinute);
		inTimeHolder.setText(msgReader.getMessage(R.string.time_picker_format,
				inHourOfDay, inMinute));
	}

	private void updateDate(final EditText inDateHolder, final int inYear,
			final int inMonth, final int inDay)
	{
		final LocalizedMsgReader msgReader = new LocalizedMsgReader(
				getActivity());
		departureDate = Calendar.getInstance();
		departureDate.set(inYear, inMonth, inDay);
		inDateHolder.setText(msgReader.getMessage(R.string.date_picker_format,
				inDay, inMonth, inYear));
	}

	private void prepareStartSpinner(final View inPrimaryTab,
			final String[] inCityNames)
	{
		final Spinner startPtSpinner = (Spinner) inPrimaryTab.findViewById(
				R.id.start_town_spinner);
		final ArrayAdapter<CharSequence> intermediatePtsAdapter =
				new ArrayAdapter<CharSequence>(getActivity(),
						android.R.layout.simple_spinner_item,
						inCityNames);
		intermediatePtsAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		startPtSpinner.setAdapter(intermediatePtsAdapter);
		startPtSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(final AdapterView<?> inParent,
					final View inView,
					final int inPos, final long inId)
			{
				fromPoint = ContentConverter.toString((TextView) inView);
			}

			@Override
			public void onNothingSelected(final AdapterView<?> inParent)
			{
				fromPoint = null;
			}
		});
	}

	private void prepareEndSpinner(final View inPrimaryTab,
			final String[] inCityNames)
	{
		final Spinner endPtSpinner = (Spinner) inPrimaryTab.findViewById(
				R.id.end_town_spinner);
		final ArrayAdapter<CharSequence> intermediatePtsAdapter =
				new ArrayAdapter<CharSequence>(getActivity(),
						android.R.layout.simple_spinner_item,
						inCityNames);
		intermediatePtsAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		endPtSpinner.setAdapter(intermediatePtsAdapter);
		endPtSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(final AdapterView<?> inParent,
					final View inView,
					final int inPos, final long inId)
			{
				final TextView selectedView = (TextView) inView;
				toPoint = ContentConverter.toString(selectedView);
			}

			@Override
			public void onNothingSelected(final AdapterView<?> inParent)
			{
				toPoint = null;
			}
		});
	}

	private void disablePrimaryFields(final View inTab)
	{
		disableField(inTab, R.id.start_town_spinner);
		disableField(inTab, R.id.end_town_spinner);
		disableField(inTab, R.id.announcement_date);
	}

	private void hideRequiredFields(final View inTab)
	{
		hideField(inTab, R.id.announcement_start_pt_required_char);
		hideField(inTab, R.id.announcement_end_pt_required_char);
		hideField(inTab, R.id.announcement_date_required_char);
		hideField(inTab, R.id.announcement_seats_required_char);
	}

	private void disableField(final View inTab, final int inFieldId)
	{
		final View fieldToDisable = inTab.findViewById(inFieldId);
		fieldToDisable.setEnabled(false);
	}

	private void hideField(final View inTab, final int inFieldId)
	{
		final View fieldToHide = inTab.findViewById(inFieldId);
		fieldToHide.setVisibility(View.INVISIBLE);
	}

	private Date getTime(final Calendar inCal)
	{
		Date result = null;

		if (null != inCal)
		{
			result = inCal.getTime();
		}

		return result;
	}

	private void prepareVehiclesSpinner(final View inSecondaryTab,
			final SpinnerAdapter inAdapter)
	{
		final Spinner vehiclesSpinner = (Spinner) inSecondaryTab.findViewById(
				R.id.vehicles);
		vehiclesSpinner.setAdapter(inAdapter);
		vehiclesSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(final AdapterView<?> inParent,
					final View inView,
					final int inPos, final long inId)
			{
				final TextView selectedView = (TextView) inView;
				selectedVehicle = ContentConverter.toString(selectedView);
			}

			@Override
			public void onNothingSelected(final AdapterView<?> inParent)
			{
				selectedVehicle = null;
			}
		});
	}

	public class IntermediatePointInserter implements OnClickListener
	{
		private static final String LAYOUT_TAG_TEMPLATE =
				"intermediate_pt_layout_";

		@Override
		public void onClick(final View v)
		{
			intermediatePtsCnt++;
			final FragmentActivity activity = getActivity();
			final LinearLayout masterLayout = (LinearLayout) activity
					.findViewById(R.id.new_announcement_layout);
			final LinearLayout intermediatePtLayout =
					(LinearLayout) getLayoutInflater(null).inflate(
							R.layout.intermediate_point_template, null);
			masterLayout.addView(intermediatePtLayout);
			final String tag = LAYOUT_TAG_TEMPLATE + intermediatePtsCnt;
			intermediatePtLayout.setTag(tag);
			final ICityComponentsPreparator preparator = new ICityComponentsPreparator()
			{
				@Override
				public void prepareComponents(final String[] inCityNames)
				{
					final Spinner spinner = (Spinner) intermediatePtLayout
							.getChildAt(1);
					System.out
							.println("===================================================");
					System.out.println("inter pts: " + intermediatePtsSpinners);
					System.out
							.println("===================================================");

					intermediatePtsSpinners.put(tag, spinner);

					HintSpinnerAdapter hintAdapter = prepareIntermediatePointAdapter(inCityNames);
					spinner.setAdapter(hintAdapter);
				}

			};
			citiesProvider.provideCityNames(preparator);

			final ImageView eraseBtn = (ImageView) intermediatePtLayout
					.getChildAt(2);
			eraseBtn.setOnClickListener(new IntermediatePointEraser());
		}
	}

	public class IntermediatePointEraser implements OnClickListener
	{
		@Override
		public void onClick(final View v)
		{
			final LinearLayout masterLayout = (LinearLayout) getActivity()
					.findViewById(R.id.new_announcement_layout);
			final LinearLayout layoutToRemove = (LinearLayout) v.getParent();
			intermediatePtsSpinners.remove(layoutToRemove.getTag().toString());
			masterLayout.removeView(layoutToRemove);
			intermediatePtsCnt--;
		}
	}
}
