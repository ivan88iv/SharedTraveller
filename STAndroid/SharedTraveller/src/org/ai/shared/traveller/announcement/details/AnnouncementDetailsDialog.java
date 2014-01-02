package org.ai.shared.traveller.announcement.details;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import eu.inmite.android.lib.dialogs.BaseDialogFragment;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class AnnouncementDetailsDialog extends SimpleDialogFragment
{
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
	private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH-mm", Locale.US);

	public static final String ANNOUNCEMENT_ARG = "announcementArg";

	public static final String DIALOG_TITLE = "Base Information";
	public static final String DIALOG_BUTTON_TEST = "OK";

	public static final String TAG = "announcement_details";

	public static void show(FragmentActivity activity, Bundle args)
	{
		AnnouncementDetailsDialog dialog = new AnnouncementDetailsDialog();
		dialog.setArguments(args);
		dialog.show(activity.getSupportFragmentManager(), TAG);
	}

	@Override
	public BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder)
	{
		IAnnouncement anno = (IAnnouncement) getArguments().getSerializable(ANNOUNCEMENT_ARG);
		builder.setTitle(DIALOG_TITLE);

		View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.announcement_details_dialog, null);
		setViewContent(dialogView, anno);
		builder.setView(dialogView);
		builder.setPositiveButton(DIALOG_BUTTON_TEST, new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ISimpleDialogListener listener = getDialogListener();
				if (listener != null)
				{
					listener.onPositiveButtonClicked(0);
				}
				dismiss();
			}
		});
		return builder;
	}

	private void setViewContent(View dialogView, IAnnouncement anno)
	{
		TextView startPoint = (TextView) dialogView.findViewById(R.id.start_point);
		startPoint.setText(anno.getFrom());

		TextView endPoint = (TextView) dialogView.findViewById(R.id.end_point);
		endPoint.setText(anno.getTo());

		TextView depDate = (TextView) dialogView.findViewById(R.id.departure_date);
		depDate.setText(DATE_FORMATTER.format(anno.getDepartureDate()));

		TextView depTime = (TextView) dialogView.findViewById(R.id.departure_time);
		depTime.setText(TIME_FORMATTER.format(anno.getDepartureTime()));

		TextView freeSeats = (TextView) dialogView.findViewById(R.id.free_seats);
		freeSeats.setText(Integer.toString(anno.getSeats()));

		TextView status = (TextView) dialogView.findViewById(R.id.status);
		status.setText(anno.getStatus());

		if (isUserLogged())
		{
			View view = dialogView.findViewById(R.id.address_container);
			view.setVisibility(View.VISIBLE);

			TextView address = (TextView) dialogView.findViewById(R.id.address);
			address.setText(anno.getDepAddress());
		}
	}

	private boolean isUserLogged()
	{
		return true;
	}
}
