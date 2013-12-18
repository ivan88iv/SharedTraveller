package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.DetailsButton;
import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.config.MapButton;
import org.ai.shared.traveller.announcement.adapter.button.config.RequestButton;
import org.shared.traveller.rest.domain.Announcement;

/**
 * Specific implementation for button rows in my travels list.
 * 
 * @author AlexanderIvanov
 * 
 */
public class MyTravelsDriverButtonRow implements IButtonRow
{

	@Override
	public IButtonConfig getFirstButton(Announcement announcement)
	{
		return new MapButton();
	}

	@Override
	public IButtonConfig getSecondButton(Announcement announcement)
	{
		return new DetailsButton();
	}

	@Override
	public IButtonConfig getThirdButton(Announcement announcement)
	{
		return new RequestButton();
	}

}
