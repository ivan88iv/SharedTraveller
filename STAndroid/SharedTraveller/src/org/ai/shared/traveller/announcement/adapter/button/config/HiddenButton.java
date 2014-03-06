package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.EmptyAction;
import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;

import android.view.View;

public class HiddenButton extends AbstractButton
{

	@Override
	public int getText()
	{
		return R.string.btn_hidden;
	}

	@Override
	public IButtonAction getAction()
	{
		return new EmptyAction();
	}

	@Override
	public int getVisability()
	{
		return View.GONE;
	}

}
