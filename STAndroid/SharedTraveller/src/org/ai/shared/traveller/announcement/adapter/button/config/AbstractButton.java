package org.ai.shared.traveller.announcement.adapter.button.config;

import android.graphics.Color;
import android.view.View;

public abstract class AbstractButton implements IButtonConfig
{

	@Override
	public int getVisability()
	{
		return View.VISIBLE;
	}

	@Override
	public int getBackgroundColor()
	{
		return Color.GRAY;
	}

}
