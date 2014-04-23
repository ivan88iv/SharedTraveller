package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.AnnoDetailsAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;

import android.graphics.Color;

public class DeclineButton extends AbstractButton<IAnnouncement>
{

	@Override
	public int getText()
	{
		return R.string.btn_decline;
	}

	@Override
	public IButtonAction<IAnnouncement> getAction()
	{
		return new AnnoDetailsAction();
	}

	@Override
	public int getBackgroundColor()
	{
		return Color.RED;
	}

}
