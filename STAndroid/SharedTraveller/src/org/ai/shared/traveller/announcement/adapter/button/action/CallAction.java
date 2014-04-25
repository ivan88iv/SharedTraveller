package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.client.domain.IAnnouncement;

import android.content.Context;
import android.widget.Toast;

public class CallAction implements IButtonAction<IAnnouncement>
{

	@Override
	public void perform(final Context cxt, final IAnnouncement announcement)
	{
		final Toast toast = Toast.makeText(cxt, "Calling ....",
				Toast.LENGTH_SHORT);
		toast.show();
	}

}
