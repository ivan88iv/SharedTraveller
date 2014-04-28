package org.ai.shared.traveller.announcement.adapter.button;

import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Composes buttons look and action depending list view context and row
 * information.
 * 
 * @author AlexanderIvanov
 * 
 */
public abstract class RowButtonComposer<T>
{

	private final IButtonRow<T> row;

	private final T anno;

	private final Context cxt;

	public RowButtonComposer(final SwipeListViewContext type,
			final T anno, final Context cxt)
	{
		super();
		this.cxt = cxt;
		this.anno = anno;
		this.row = getButtonRow(type);
	}

	protected abstract IButtonRow<T> getButtonRow(
			final SwipeListViewContext type);

	public void configFirstButton(final Button button)
	{
		final IButtonConfig<T> buttonConfig = row.getFirstButton(anno);
		configButton(button, buttonConfig);
	}

	public void configSecondButton(final Button button)
	{
		final IButtonConfig<T> buttonConfig = row.getSecondButton(anno);
		configButton(button, buttonConfig);
	}

	public void configThirdButton(final Button button)
	{
		final IButtonConfig<T> buttonConfig = row.getThirdButton(anno);
		configButton(button, buttonConfig);
	}

	private void configButton(final Button button,
			final IButtonConfig<T> buttonConfig)
	{
		button.setText(buttonConfig.getText());
		button.setVisibility(buttonConfig.getVisability());
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(final View v)
			{
				buttonConfig.getAction().perform(cxt, anno);
			}
		});
		button.setBackgroundColor(buttonConfig.getBackgroundColor());
	}
}
