package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.config.MyRequestsButton;
import org.shared.traveller.client.domain.request.IRequestInfo;

/**
 * Specific implementation for button rows in my travels list.
 * 
 * @author AlexanderIvanov
 * 
 */
public class MyTravelsDriverButtonRow implements IButtonRow<IRequestInfo>
{

	@Override
	public IButtonConfig<IRequestInfo> getFirstButton(
			final IRequestInfo inRequest)
	{
		return new MyRequestsButton();
	}

	@Override
	public IButtonConfig<IRequestInfo> getSecondButton(
			final IRequestInfo inRequest)
	{
		return new MyRequestsButton();
	}

	@Override
	public IButtonConfig<IRequestInfo> getThirdButton(
			final IRequestInfo inRequest)
	{
		return new MyRequestsButton();
	}
}
