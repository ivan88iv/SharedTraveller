package org.ai.shared.traveller.announcement.input.tab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.content.converter.ContentConverter;
import org.ai.sharedtraveller.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SecondaryTab extends Fragment
{
    private static int intermediatePtsCnt = 0;

    private String selectedVehicle;

    private EditText departureAddress;

    private final Map<String, Spinner> intermediatePtsSpinners = new
            HashMap<String, Spinner>();

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState)
    {
        final View secondaryTab = inflater
                .inflate(R.layout.announcement_secondary_tab, null);

        final ArrayAdapter<CharSequence> vehiclesAdapter =
                ArrayAdapter.createFromResource(getActivity(),
                        R.array.vehicles_array,
                        android.R.layout.simple_spinner_item);
        vehiclesAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        prepareVehiclesSpinner(secondaryTab, vehiclesAdapter);
        final Button newIntermediatePtBtn = (Button) secondaryTab.findViewById(
                R.id.new_intermediate_pt_btn);
        newIntermediatePtBtn.setOnClickListener(
                new SecondaryTab.IntermediatePointInserter());

        departureAddress = (EditText) secondaryTab
                .findViewById(R.id.departure_address);

        return secondaryTab;
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
                    .findViewById(R.id.secondary_tab_layout);
            final LinearLayout intermediatePtLayout =
                    (LinearLayout) getLayoutInflater(null).inflate(
                            R.layout.intermediate_point_template, null);
            intermediatePtLayout.setTag(LAYOUT_TAG_TEMPLATE +
                    intermediatePtsCnt);
            masterLayout.addView(intermediatePtLayout);
            final Spinner spinner = (Spinner) intermediatePtLayout
                    .getChildAt(1);
            intermediatePtsSpinners.put(spinner.getTag().toString(),
                    spinner);
            final ArrayAdapter<CharSequence> intermediatePtsAdapter =
                    ArrayAdapter.createFromResource(activity,
                            R.array.towns_array,
                            android.R.layout.simple_spinner_item);
            intermediatePtsAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(intermediatePtsAdapter);

            final ImageButton eraseBtn = (ImageButton) intermediatePtLayout
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
                    .findViewById(R.id.secondary_tab_layout);
            final Spinner spinner = (Spinner) masterLayout.getChildAt(1);
            intermediatePtsSpinners.remove(spinner.getTag().toString());
            masterLayout.removeView((LinearLayout) v.getParent());
            intermediatePtsCnt--;
        }
    }
}
