package org.ai.shared.traveller.command.request;

import org.shared.traveller.client.domain.rest.event.NewRequestEvent;

public interface INewRequestCommand
{
    void sendRequest(final NewRequestEvent inEvent);
}
