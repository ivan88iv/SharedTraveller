package org.ai.shared.traveller.announcement.date;

import java.io.Serializable;

import android.widget.DatePicker;

/**
 * Allows listener to be attached on date picker and when date is selected the
 * listener is notified.
 * 
 * @author AlexanderIvanov
 * 
 */
public interface IOnDateSetListener extends Serializable
{
	public void onDateSet(DatePicker view, int year, int month, int day);
}
