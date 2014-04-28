package org.ai.shared.traveller.command.request;

import org.shared.traveller.client.domain.rest.RequestInfo;

public interface INewRequestCommand
{
	void sendRequest(final RequestInfo inEvent);
}
