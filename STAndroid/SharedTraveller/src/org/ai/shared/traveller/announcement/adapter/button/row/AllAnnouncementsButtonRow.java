package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.DeclineButton;
import org.ai.shared.traveller.announcement.adapter.button.config.DetailsButton;
import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.config.MapButton;
import org.ai.shared.traveller.announcement.adapter.button.config.RequestButton;
import org.shared.traveller.client.domain.IAnnouncement;

/**
 * Specific implementation for button rows in all announcements list.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AllAnnouncementsButtonRow implements IButtonRow<IAnnouncement>
{

	@Override
	public IButtonConfig<IAnnouncement> getFirstButton(
			final IAnnouncement announcement)
	{
		return new MapButton();
	}

	@Override
	public IButtonConfig<IAnnouncement> getSecondButton(
			final IAnnouncement announcement)
	{
		return new DetailsButton();
	}

	@Override
	public IButtonConfig<IAnnouncement> getThirdButton(
			final IAnnouncement announcement)
	{
		if (announcement.getFrom().equals("from3"))
		{
			return new DeclineButton();

		}
		return new RequestButton();
	}

}
