package org.ai.shared.traveller.announcement.adapter.button.factory;

import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.button.row.MyTravelsDriverButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.shared.traveller.client.domain.request.IRequestInfo;

/**
 * Creates specific button {@link IButtonRow row} configuration container
 * depending list view context. If needed factory method params can be extended
 * or changed.
 * 
 * @author AlexanderIvanov
 * 
 */
public class RequestButtonRowFactory
{

	public static IButtonRow<IRequestInfo> getButtonRow(
			SwipeListViewContext type)
	{
		IButtonRow<IRequestInfo> row = null;
		switch (type)
		{
		case MY_TRAVELS_TRAVELLER:
			row = getMyTravelsRow();
			break;
		default:
			break;
		}

		return row;
	}

	private static IButtonRow<IRequestInfo> getMyTravelsRow()
	{
		return new MyTravelsDriverButtonRow();
	}
}
