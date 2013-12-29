package org.ai.shared.traveller.formatter;

import java.text.MessageFormat;

import org.ai.sharedtraveller.R;

import android.view.View;
import android.widget.TextView;

public class LabelsFormatter
{
    private final View view;

    public LabelsFormatter(final View inView)
    {
        view = inView;
    }

    public void format(final int[] inLabelIds)
    {
        for (final int id : inLabelIds)
        {
            final TextView currTextView = (TextView) view
                    .findViewById(id);
            if (null != currTextView)
            {
                final String formatStr =
                        view.getResources().getString(
                                R.string.labels_format);
                currTextView.setText(MessageFormat.format(formatStr,
                        currTextView
                                .getText().toString()));
            }
        }
    }
}
