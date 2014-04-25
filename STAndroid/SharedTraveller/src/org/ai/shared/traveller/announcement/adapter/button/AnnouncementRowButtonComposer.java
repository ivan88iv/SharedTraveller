package org.ai.shared.traveller.announcement.adapter.button;

import org.ai.shared.traveller.announcement.adapter.button.factory.AnnouncementButtonRowFactory;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.shared.traveller.client.domain.IAnnouncement;

import android.content.Context;

public class AnnouncementRowButtonComposer extends
		RowButtonComposer<IAnnouncement>
{

	public AnnouncementRowButtonComposer(SwipeListViewContext type,
			IAnnouncement anno, Context cxt)
	{
		super(type, anno, cxt);
	}

	@Override
	protected IButtonRow<IAnnouncement> getButtonRow(final SwipeListViewContext type)
	{
		return AnnouncementButtonRowFactory.getButtonRow(type);
	}

}
