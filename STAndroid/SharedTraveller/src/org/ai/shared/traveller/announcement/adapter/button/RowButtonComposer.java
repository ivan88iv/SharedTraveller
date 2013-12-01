package org.ai.shared.traveller.announcement.adapter.button;

import org.ai.shared.traveller.announcement.adapter.button.config.IButtonConfig;
import org.ai.shared.traveller.announcement.adapter.button.factory.ButtonRowFactory;
import org.ai.shared.traveller.announcement.adapter.button.row.IButtonRow;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.shared.traveller.rest.domain.Announcement;

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
public class RowButtonComposer
{

	private IButtonRow row;

	private Announcement anno;

	private Context cxt;

	public RowButtonComposer(SwipeListViewContext type, Announcement anno, Context cxt)
	{
		super();
		this.cxt = cxt;
		this.anno = anno;
		this.row = ButtonRowFactory.getButtonRow(type);
	}

	public void configFirstButton(Button button)
	{
		final IButtonConfig buttonConfig = row.getFirstButton(anno);
		configButton(button, buttonConfig);
	}

	public void configSecondButton(Button button)
	{
		final IButtonConfig buttonConfig = row.getSecondButton(anno);
		configButton(button, buttonConfig);
	}

	public void configThirdButton(Button button)
	{
		final IButtonConfig buttonConfig = row.getThirdButton(anno);
		configButton(button, buttonConfig);
	}

	private void configButton(Button button, final IButtonConfig buttonConfig)
	{
		button.setText(buttonConfig.getText());
		button.setVisibility(buttonConfig.getVisability());
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				buttonConfig.getAction().perform(cxt, anno);
			}
		});
		button.setBackgroundColor(buttonConfig.getBackgroundColor());
	}
}
