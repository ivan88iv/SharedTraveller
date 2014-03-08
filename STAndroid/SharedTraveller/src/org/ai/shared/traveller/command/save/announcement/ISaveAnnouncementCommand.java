package org.ai.shared.traveller.command.save.announcement;

import org.shared.traveller.client.domain.rest.Announcement;

public interface ISaveAnnouncementCommand
{
    void saveAnnouncement(final Announcement inAnnouncement);
}
