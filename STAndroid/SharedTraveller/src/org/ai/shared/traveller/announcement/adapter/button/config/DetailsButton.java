package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.AnnoDetailsAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;

public class DetailsButton extends AbstractButton<IAnnouncement>
{

	@Override
	public int getText()
	{
		return R.string.btn_detils;
	}

	@Override
	public IButtonAction<IAnnouncement> getAction()
	{
		return new AnnoDetailsAction();
	}

}
