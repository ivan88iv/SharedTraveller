package org.ai.shared.traveller.announcement.input.save;

import org.shared.traveller.client.domain.rest.Announcement;

public interface ISaveAnnouncementCommand
{
    void saveAnnouncement(final Announcement inAnnouncement);
}
