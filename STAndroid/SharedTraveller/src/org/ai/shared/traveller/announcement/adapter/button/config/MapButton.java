package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.shared.traveller.announcement.adapter.button.action.ShowMapAction;
import org.ai.sharedtraveller.R;

public class MapButton extends AbstractButton
{

	@Override
	public int getText()
	{
		return R.string.btn_map;
	}

	@Override
	public IButtonAction getAction()
	{
		return new ShowMapAction();
	}

}
