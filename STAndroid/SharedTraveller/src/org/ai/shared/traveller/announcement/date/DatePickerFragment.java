package org.ai.shared.traveller.announcement.date;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * Fragment displaying date picker.
 * 
 * @author AlexanderIvanov
 * 
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	public static final String DATE_PICKER_LISTENER = "datePickerListener";

	private int year;
	private int month;
	private int day;
	private IOnDateSetListener listener;

	public DatePickerFragment()
	{
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		listener = (IOnDateSetListener) getArguments().get(DATE_PICKER_LISTENER);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		Calendar now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);
		day = now.get(Calendar.DATE);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		listener.onDateSet(view, year, month, day);
	}

}
