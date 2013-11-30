package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.AnnoDetailsAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;

public class DetailsButton extends AbstractButton
{

	@Override
	public int getText()
	{
		return R.string.btn_detils;
	}

	@Override
	public IButtonAction getAction()
	{
		return new AnnoDetailsAction();
	}

}
