package org.ai.shared.traveller.announcement.input.tab;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.ai.shared.traveller.content.converter.ContentConverter;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.dialog.pickers.AbstractDatePickerDisplayer;
import org.ai.shared.traveller.dialog.pickers.AbstractPickerDisplayer;
import org.ai.shared.traveller.dialog.pickers.AbstractTimePickerDisplayer;
import org.ai.shared.traveller.formatter.LabelsFormatter;
import org.ai.shared.traveller.msg.reader.LocalizedMsgReader;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.sharedtraveller.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

public class PrimaryTab extends Fragment
{
    private ICitiesProvider citiesProvider;

    private boolean disablePrimaryFields;

    private boolean showRequiredFields;

    private String fromPoint;

    private String toPoint;

    private Calendar departureDate;

    private Calendar depeartureTime;

    private EditText priceHolder;

    private EditText seats;

    private final int[] labelIds =
    {
            R.id.ann_start_pt_label, R.id.ann_end_pt_label,
            R.id.ann_date_label, R.id.ann_time_label,
            R.id.ann_seats_label, R.id.ann_price_label
    };

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
        super.onAttach(activity);
    }

    public static PrimaryTab newInstance(final boolean inDisablePrimaryFields,
            final boolean inShowRequiredFields)
    {
        final PrimaryTab primaryTab = new PrimaryTab();
        primaryTab.disablePrimaryFields = inDisablePrimaryFields;
        primaryTab.showRequiredFields = inShowRequiredFields;

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

        return primaryTab;
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
                final ArrayAdapter<CharSequence> townsAdapter =
                        new ArrayAdapter<CharSequence>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                inCityNames);
                townsAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                prepareStartSpinner(inPrimaryTab, townsAdapter);
                prepareEndSpinner(inPrimaryTab, townsAdapter);
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

        final LabelsFormatter formatter = new LabelsFormatter(inPrimaryTab);
        formatter.format(labelIds);
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
            final SpinnerAdapter inSpinnerAdapter)
    {
        final Spinner startPtSpinner = (Spinner) inPrimaryTab.findViewById(
                R.id.start_town_spinner);
        startPtSpinner.setAdapter(inSpinnerAdapter);
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
            final SpinnerAdapter inSpinnerAdapter)
    {
        final Spinner endPtSpinner = (Spinner) inPrimaryTab.findViewById(
                R.id.end_town_spinner);
        endPtSpinner.setAdapter(inSpinnerAdapter);
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
}
