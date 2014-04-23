package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.client.domain.IAnnouncement;

import android.content.Context;
import android.widget.Toast;

public class ShowMapAction implements IButtonAction<IAnnouncement>
{

	@Override
	public void perform(final Context cxt, final IAnnouncement announcement)
	{
		final Toast toast = Toast.makeText(cxt, "Show map", Toast.LENGTH_SHORT);
		toast.show();
	}

}
