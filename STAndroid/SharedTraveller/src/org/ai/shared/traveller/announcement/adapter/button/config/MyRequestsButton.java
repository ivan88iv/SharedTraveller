package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.request.IRequestInfo;

public class MyRequestsButton extends AbstractButton<IRequestInfo>
{

	@Override
	public int getText()
	{
		return R.string.my_requests;
	}

	@Override
	public IButtonAction<IRequestInfo> getAction()
	{
		return new RequestAction();
	}

}
