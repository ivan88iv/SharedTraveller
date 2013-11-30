package org.ai.shared.traveller.announcement.adapter.button.factory;

import org.ai.shared.traveller.announcement.adapter.button.row.AnnoReviewButtonRow;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewType;

/**
 * Creates specific button {@link IButtonRow row} configuration container
 * depending list view context. If needed factory method params can be extended
 * or changed.
 * 
 * @author AlexanderIvanov
 * 
 */
public class ButtonRowFactory
{

	private ButtonRowFactory()
	{

	}

	public static IButtonRow getButtonRow(SwipeListViewType type)
	{
		IButtonRow row = null;
		switch (type)
		{
		case ANNOUNCEMENT:
			row = getAnnoiuncementRow();
			break;
		case MY_TRAVELS_DRIVER:
			// create needed buttons row implementation
			break;
		case MY_TRAVELS_TRAVELLER:
			// create needed buttons row implementation
			break;
		default:
			break;
		}

		return row;
	}

	private static IButtonRow getAnnoiuncementRow()
	{
		return new AnnoReviewButtonRow();
	}
}
