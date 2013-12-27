package org.ai.shared.traveller.announcement.adapter.button.row;

import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.shared.traveller.client.domain.rest.Announcement;

/**
 * Represents contextual visualization of row in the swipe list view. The
 * context will be for example "My Announcements", "Search",
 * "All announcements". Each context will be represented with specific
 * implementation which will decide what button will be shown based on the
 * information for current row. For now i think announcement information will be
 * enough for button choice.
 * 
 * @author AlexanderIvanov
 * 
 */
public interface IButtonRow
{
    /**
     * Decide what button configuration will be needed for first button.
     * 
     * @param announcement
     * @return
     */
    public IButtonConfig getFirstButton(Announcement announcement);

    /**
     * Decide what button configuration will be needed for second button.
     * 
     * @param announcement
     * @return
     */
    public IButtonConfig getSecondButton(Announcement announcement);

    /**
     * Decide what button configuration will be needed for third button.
     * 
     * @param announcement
     * @return
     */
    public IButtonConfig getThirdButton(Announcement announcement);
}
