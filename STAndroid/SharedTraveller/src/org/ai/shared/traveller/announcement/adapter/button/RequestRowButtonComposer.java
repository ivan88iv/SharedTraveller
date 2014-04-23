package org.ai.shared.traveller.announcement.adapter.button;

import org.ai.shared.traveller.announcement.adapter.button.factory.RequestButtonRowFactory;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.shared.traveller.client.domain.request.IRequestInfo;

import android.content.Context;

public class RequestRowButtonComposer extends
		RowButtonComposer<IRequestInfo>
{

	public RequestRowButtonComposer(SwipeListViewContext type,
			IRequestInfo anno, Context cxt)
	{
		super(type, anno, cxt);
	}

	@Override
	protected IButtonRow<IRequestInfo> getButtonRow(
			final SwipeListViewContext type)
	{
		return RequestButtonRowFactory.getButtonRow(type);
	}

}
