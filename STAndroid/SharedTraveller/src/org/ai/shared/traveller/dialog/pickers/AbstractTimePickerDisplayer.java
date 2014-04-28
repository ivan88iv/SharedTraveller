package org.ai.shared.traveller.dialog.pickers;

import org.ai.shared.traveller.dialog.pickers.time.TimePickerFragment;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v4.app.FragmentManager;
import android.widget.TimePicker;

public abstract class AbstractTimePickerDisplayer extends
		AbstractPickerDisplayer
{
	@Override
	protected void showPicker(final FragmentManager inManager)
	{
		final TimePickerFragment timeFragment = new TimePickerFragment();
		timeFragment.setOnTimeSetListener(new OnTimeSetListener()
		{
			@Override
			public void onTimeSet(final TimePicker inView,
					final int inHourOfDay,
					final int inMinute)
			{
				onTimeSelection(inView, inHourOfDay, inMinute);
			}
		});
		timeFragment.show(inManager, "timePicker");
	}

	public abstract void onTimeSelection(final TimePicker inView,
			final int inHourOfDay,
			final int inMinute);
}
