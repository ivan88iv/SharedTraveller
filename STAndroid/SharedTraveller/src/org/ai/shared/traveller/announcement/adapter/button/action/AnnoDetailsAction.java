package org.ai.shared.traveller.announcement.adapter.button.action;

import org.ai.shared.traveller.announcement.details.AnnouncementDetailsDialog;
import org.shared.traveller.client.domain.IAnnouncement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class AnnoDetailsAction implements IButtonAction<IAnnouncement>
{

	@Override
	public void perform(final Context cxt, final IAnnouncement announcement)
	{
		Bundle args = new Bundle();
		args.putSerializable(AnnouncementDetailsDialog.ANNOUNCEMENT_ARG,
				announcement);
		AnnouncementDetailsDialog.show((FragmentActivity) cxt, args);
	}

}
