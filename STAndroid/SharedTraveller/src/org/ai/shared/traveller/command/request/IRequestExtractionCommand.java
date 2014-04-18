package org.ai.shared.traveller.command.request;

import org.ai.shared.traveller.request.RequestsAdapter;
import org.shared.traveller.client.domain.IAnnouncement;

public interface IRequestExtractionCommand
{
    void extractRequests(final IAnnouncement inAnnouncement,
            final RequestsAdapter inAdapter);
}
