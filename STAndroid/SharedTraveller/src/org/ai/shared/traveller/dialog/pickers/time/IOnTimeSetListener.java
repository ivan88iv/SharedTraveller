package org.ai.shared.traveller.dialog.pickers.time;

import java.io.Serializable;

import android.widget.TimePicker;

public interface IOnTimeSetListener extends Serializable
{
    public void onTimeSet(TimePicker inView, int inHourOfDay, int inMinute);
}
