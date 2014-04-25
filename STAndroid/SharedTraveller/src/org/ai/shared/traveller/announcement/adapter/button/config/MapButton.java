package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.shared.traveller.announcement.adapter.button.action.ShowMapAction;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;

public class MapButton extends AbstractButton<IAnnouncement>
{

	@Override
	public int getText()
	{
		return R.string.btn_map;
	}

	@Override
	public IButtonAction<IAnnouncement> getAction()
	{
		return new ShowMapAction();
	}

}
