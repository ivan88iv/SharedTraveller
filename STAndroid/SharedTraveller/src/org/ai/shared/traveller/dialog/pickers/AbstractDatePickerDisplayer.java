package org.ai.shared.traveller.dialog.pickers;

import org.ai.shared.traveller.announcement.date.DatePickerFragment;

import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

public abstract class AbstractDatePickerDisplayer extends
        AbstractPickerDisplayer
{
    @Override
    protected void showPicker(final FragmentManager inManager)
    {
        final DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.setOnDateSetListener(new OnDateSetListener()
        {
            @Override
            public void onDateSet(final DatePicker inView, final int inYear,
                    final int inMonthOfYear,
                    final int inDayOfMonth)
            {
                onDateSelecion(inView, inDayOfMonth, inMonthOfYear, inYear);
            }
        });

        dateFragment.show(inManager, "datePicker");
    }

    public abstract void onDateSelecion(final DatePicker inView,
            final int inYear,
            final int inMonthOfYear,
            final int inDayOfMonth);
}
