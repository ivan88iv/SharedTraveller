package org.ai.shared.traveller.announcement.adapter.button.action;

import org.shared.traveller.client.domain.rest.Announcement;

import android.content.Context;
import android.view.View;

/**
 * Represents command object which will be called in
 * {@link View.OnClickListener button} on click listener and will perform button
 * action. For now I think that {@link Context context} and {@link Announcement
 * annooucement} params are enough for performing all needed actions. If not can
 * be changed or extended.
 * 
 * @author AlexanderIvanov
 * 
 */
public interface IButtonAction
{
    public void perform(Context cxt, Announcement announcement);
}
