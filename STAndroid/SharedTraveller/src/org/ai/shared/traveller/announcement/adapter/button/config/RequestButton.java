package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.AnnoRequestAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;

public class RequestButton extends AbstractButton<IAnnouncement>
{

	@Override
	public int getText()
	{
		return R.string.btn_request;
	}

	@Override
	public IButtonAction<IAnnouncement> getAction()
	{
		return new AnnoRequestAction();
	}

}
