package org.ai.shared.traveller.dialog.pickers.time;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimePickerFragment extends DialogFragment
{
    public static final String TIME_PICKER_LISTENER = "timePickerListener";

    private int hourOfDay;

    private int minute;

    private OnTimeSetListener listener;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState)
    {
        // listener = (IOnTimeSetListener) getArguments()
        // .get(TIME_PICKER_LISTENER);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        final Calendar now = Calendar.getInstance();
        hourOfDay = now.get(Calendar.HOUR_OF_DAY);
        minute = now.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), listener, hourOfDay, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    // @Override
    // public void onTimeSet(final TimePicker view, final int inHourOfDay,
    // final int inMinute)
    // {
    // if (null != listener)
    // {
    // listener.onTimeSet(view, inHourOfDay, inMinute);
    // }
    // }

    public void setOnTimeSetListener(final OnTimeSetListener inListener)
    {
        listener = inListener;
    }
}
