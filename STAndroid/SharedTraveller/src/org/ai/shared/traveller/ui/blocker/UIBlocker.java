package org.ai.shared.traveller.ui.blocker;

import org.shared.traveller.utility.InstanceAsserter;

import android.view.View;

/**
 * The class is responsible for displaying or hiding a loading indicator
 * instance and blocking/unblocking the user interactions for this activity
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class UIBlocker
{
	private final View progressIndicator;

	/**
	 * Creates a new blocker instance
	 * 
	 * @param inProgressIndicator
	 *            the progress indicator element which is associated with the
	 *            current blocker element. It may not be null
	 */
	public UIBlocker(final View inProgressIndicator)
	{
		InstanceAsserter.assertNotNull(inProgressIndicator,
				"progress indicator");

		progressIndicator = inProgressIndicator;
	}

	/**
	 * Blocks or unblocks the UI depending on the specified flag
	 * 
	 * @param inBlock
	 *            if true the UI is blocked. Otherwise it is unblocked
	 */
	public void block(final boolean inBlock)
	{
		if (inBlock)
		{
			progressIndicator.setVisibility(View.VISIBLE);
		} else
		{
			progressIndicator.setVisibility(View.GONE);
		}
	}
}
