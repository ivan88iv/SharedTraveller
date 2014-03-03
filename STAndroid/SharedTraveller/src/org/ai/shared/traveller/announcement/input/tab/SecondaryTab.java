package org.ai.shared.traveller.announcement.input.tab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.content.converter.ContentConverter;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.formatter.LabelsFormatter;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;

import android.app.Activity;
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

    private IVehiclesProvider vehiclesProvider;

    private ICitiesProvider citiesProvider;

    private String selectedVehicle;

    private EditText departureAddress;

    private final Map<String, Spinner> intermediatePtsSpinners = new
            HashMap<String, Spinner>();

    private final int[] labelIds =
    {
            R.id.ann_dep_address, R.id.ann_intermediate_pts_label,
            R.id.ann_vehicle_label
    };

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

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState)
    {
        final View secondaryTab = inflater
                .inflate(R.layout.announcement_secondary_tab, null);
        final IVehicleComponentsPreparator preparator = new IVehicleComponentsPreparator()
        {
            @Override
            public void prepareComponents(final String[] inVehicleNames)
            {
                final ArrayAdapter<CharSequence> vehiclesAdapter =
                        new ArrayAdapter<CharSequence>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                inVehicleNames);
                vehiclesAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                prepareVehiclesSpinner(secondaryTab, vehiclesAdapter);
            }
        };
        // TODO the username must come from somewhere
        vehiclesProvider.provideVehicleNames("temp", preparator);

        final Button newIntermediatePtBtn = (Button) secondaryTab.findViewById(
                R.id.new_intermediate_pt_btn);
        newIntermediatePtBtn.setOnClickListener(
                new SecondaryTab.IntermediatePointInserter());

        departureAddress = (EditText) secondaryTab
                .findViewById(R.id.departure_address);

        final LabelsFormatter formatter = new LabelsFormatter(secondaryTab);
        formatter.format(labelIds);

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
                    final ArrayAdapter<CharSequence> intermediatePtsAdapter =
                            new ArrayAdapter<CharSequence>(activity,
                                    android.R.layout.simple_spinner_item,
                                    inCityNames);
                    intermediatePtsAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(intermediatePtsAdapter);
                }
            };
            citiesProvider.provideCityNames(preparator);

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
            final LinearLayout layoutToRemove = (LinearLayout) v.getParent();
            intermediatePtsSpinners.remove(layoutToRemove.getTag().toString());
            masterLayout.removeView(layoutToRemove);
            intermediatePtsCnt--;
        }
    }
}
