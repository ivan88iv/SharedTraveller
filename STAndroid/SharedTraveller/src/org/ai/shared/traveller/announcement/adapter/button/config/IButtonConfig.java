package org.ai.shared.traveller.announcement.adapter.button.config;

import org.ai.shared.traveller.announcement.adapter.button.action.IButtonAction;

/**
 * Represents all properties that must be configured for a button in swipe list
 * view. Specific implementation will be created for each different button type.
 * (e.g. "Call", "Request", "Details")
 * 
 * @author AlexanderIvanov
 * 
 */
public interface IButtonConfig
{

	public int getText();

	public int getVisability();

	public IButtonAction getAction();

	// This can be changed to drawable to style if needed
	public int getBackgroundColor();
}
