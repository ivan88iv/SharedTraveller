package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.CallButton;
import org.ai.shared.traveller.announcement.adapter.button.config.DeclineButton;
import org.ai.shared.traveller.announcement.adapter.button.config.DetailsButton;
import org.ai.shared.traveller.announcement.adapter.button.config.HiddenButton;
import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.config.MapButton;
import org.ai.shared.traveller.announcement.adapter.button.config.RequestButton;
import org.shared.traveller.rest.domain.Announcement;

/**
 * Specific implementation for button rows in all announcements list.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AnnoReviewButtonRow implements IButtonRow
{

	@Override
	public IButtonConfig getFirstButton(Announcement announcement)
	{
		return new MapButton();
	}

	@Override
	public IButtonConfig getSecondButton(Announcement announcement)
	{
		if (announcement.getFrom().equals("from2"))
		{
			return new CallButton();
		}
		if (announcement.getFrom().equals("from3"))
		{
			return new HiddenButton();

		}
		return new DetailsButton();
	}

	@Override
	public IButtonConfig getThirdButton(Announcement announcement)
	{
		if (announcement.getFrom().equals("from3"))
		{
			return new DeclineButton();

		}
		return new RequestButton();
	}

}
