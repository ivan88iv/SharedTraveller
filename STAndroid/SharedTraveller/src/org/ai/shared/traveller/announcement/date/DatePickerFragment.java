package org.ai.shared.traveller.announcement.date;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment displaying date picker.
 * 
 * @author AlexanderIvanov
 * 
 */
public class DatePickerFragment extends DialogFragment
{
    public static final String DATE_PICKER_LISTENER = "datePickerListener";

    private int year;

    private int month;

    private int day;

    // private IOnDateSetListener listener;

    private DatePickerDialog.OnDateSetListener listener;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState)
    {
        // listener = (IOnDateSetListener) getArguments()
        // .get(DATE_PICKER_LISTENER);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        final Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DATE);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    // @Override
    // public void onDateSet(final DatePicker view, final int year,
    // final int month, final int day)
    // {
    // if (null != listener)
    // {
    // listener.onDateSet(view, year, month, day);
    // }
    // }

    public void setOnDateSetListener(
            final DatePickerDialog.OnDateSetListener inListener)
    {
        listener = inListener;
    }
}
