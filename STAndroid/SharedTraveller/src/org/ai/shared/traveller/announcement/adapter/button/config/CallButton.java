package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.CallAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;

import android.graphics.Color;

public class CallButton extends AbstractButton
{

	@Override
	public int getText()
	{
		return R.string.btn_call;
	}

	@Override
	public IButtonAction getAction()
	{
		return new CallAction();
	}

	@Override
	public int getBackgroundColor()
	{
		return Color.GREEN;
	}
}
