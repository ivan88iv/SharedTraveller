package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.rest.domain.Announcement;

import android.content.Context;
import android.widget.Toast;

public class DeclineAction implements IButtonAction
{

	@Override
	public void perform(Context cxt, Announcement announcement)
	{
		Toast toast = Toast.makeText(cxt, "Decline request", Toast.LENGTH_SHORT);
		toast.show();
	}
}
