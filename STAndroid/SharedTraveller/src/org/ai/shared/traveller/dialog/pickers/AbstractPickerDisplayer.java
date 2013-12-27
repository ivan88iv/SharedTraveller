package org.ai.shared.traveller.dialog.pickers;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

public abstract class AbstractPickerDisplayer
{
    public void display(final FragmentManager inManager, final TextView inView)
    {
        // make the field non-editable
        inView.setKeyListener(null);
        inView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                showPicker(inManager);
            }
        });
        inView.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus)
            {
                if (hasFocus)
                {
                    showPicker(inManager);
                }
            }
        });
    }

    protected abstract void showPicker(final FragmentManager inManager);
}
