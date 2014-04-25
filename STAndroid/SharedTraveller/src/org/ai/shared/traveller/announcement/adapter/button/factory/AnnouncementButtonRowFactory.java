package org.ai.shared.traveller.announcement.adapter.button.factory;

import org.ai.shared.traveller.announcement.adapter.button.row.AllAnnouncementsButtonRow;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.shared.traveller.client.domain.IAnnouncement;

/**
 * Creates specific button {@link IButtonRow row} configuration container
 * depending list view context. If needed factory method params can be extended
 * or changed.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AnnouncementButtonRowFactory
{

	public static IButtonRow<IAnnouncement> getButtonRow(
			SwipeListViewContext type)
	{
		IButtonRow<IAnnouncement> row = null;
		switch (type)
		{
		case ANNOUNCEMENT:
			row = getAnnoiuncementRow();
			break;
		default:
			break;
		}

		return row;
	}

	private static IButtonRow<IAnnouncement> getAnnoiuncementRow()
	{
		return new AllAnnouncementsButtonRow();
	}
}
