package org.ai.shared.traveller;

import org.ai.sharedtraveller.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SecondActivity extends Fragment
{
    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState)
    {
        final View v = inflater.inflate(R.layout.layout2, null);
        return v;
    }
}
