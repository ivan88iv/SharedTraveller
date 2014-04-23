package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.client.domain.IAnnouncement;

import android.content.Context;

public class EmptyAction implements IButtonAction<IAnnouncement>
{

	@Override
	public void perform(final Context cxt, final IAnnouncement announcement)
	{
	}

}
