package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.DeclineButton;
import org.ai.shared.traveller.announcement.adapter.button.config.DetailsButton;
import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.config.MapButton;
import org.ai.shared.traveller.announcement.adapter.button.config.RequestButton;
import org.shared.traveller.client.domain.rest.Announcement;

/**
 * Specific implementation for button rows in all announcements list.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AllAnnouncementsButtonRow implements IButtonRow
{

	@Override
	public IButtonConfig getFirstButton(final Announcement announcement)
	{
		return new MapButton();
	}

	@Override
	public IButtonConfig getSecondButton(final Announcement announcement)
	{
		return new DetailsButton();
	}

	@Override
	public IButtonConfig getThirdButton(final Announcement announcement)
	{
		if (announcement.getFrom().equals("from3"))
		{
			return new DeclineButton();

		}
		return new RequestButton();
	}

}
