package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.AnnoRequestAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;

public class RequestButton extends AbstractButton
{

	@Override
	public int getText()
	{
		return R.string.btn_request;
	}

	@Override
	public IButtonAction getAction()
	{
		return new AnnoRequestAction();
	}

}
