package org.ai.shared.traveller.announcement.adapter;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.ai.shared.traveller.announcement.adapter.button.AnnouncementRowButtonComposer;
import org.ai.shared.traveller.announcement.adapter.button.RowButtonComposer;
import org.ai.shared.traveller.announcement.adapter.http.IAdapterHttpTask;
import org.ai.shared.traveller.announcement.adapter.type.SwipeListViewContext;
import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.rest.domain.CountedResponseList;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;

public class AllAnnouncementsLazyLoadingAdapter extends
		LazyLoadingAdapter<IAnnouncement>
{

	static class ViewHolder
	{
		LinearLayout container;
		TextView from;
		TextView to;
		TextView date;
		Button bAction1;
		Button bAction2;
		Button bAction3;
	}

	protected IAdapterHttpTask<ServerResponse<? extends CountedResponseList<IAnnouncement>>> httpTask;

	public AllAnnouncementsLazyLoadingAdapter(
			Context ctxt,
			ArrayList<IAnnouncement> list,
			IAdapterHttpTask<ServerResponse<? extends CountedResponseList<IAnnouncement>>> httpTask)
	{
		super(ctxt, list);
		this.httpTask = httpTask;
	}

	@Override
	protected View getDataView(final int position, View convertView,
			final ViewGroup parent)
	{
		final IAnnouncement item = (IAnnouncement) getItem(position);

		if (item != null)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				final LayoutInflater li = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = li.inflate(R.layout.swipe_list_view_row, parent,
						false);
				holder = new ViewHolder();
				holder.container = (LinearLayout) convertView
						.findViewById(R.id.announcement_container);
				holder.from = (TextView) convertView.findViewById(R.id.from);
				holder.to = (TextView) convertView.findViewById(R.id.to);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.bAction1 = (Button) convertView
						.findViewById(R.id.row_b_action_1);
				holder.bAction2 = (Button) convertView
						.findViewById(R.id.row_b_action_2);
				holder.bAction3 = (Button) convertView
						.findViewById(R.id.row_b_action_3);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			((SwipeListView) parent).recycle(convertView, position);

			holder.container.setVisibility(View.VISIBLE);
			holder.from.setText(item.getFrom());
			holder.to.setText(item.getTo());
			holder.date.setText(item.getDepartureDate().toString());

			// SwipeListViewType.ANNOUNCEMENT must be passes as parameter
			// from the view that creates the adapter
			final RowButtonComposer<IAnnouncement> composer = new AnnouncementRowButtonComposer(
					SwipeListViewContext.ANNOUNCEMENT, item, getContext());
			composer.configFirstButton(holder.bAction1);
			composer.configSecondButton(holder.bAction2);
			composer.configThirdButton(holder.bAction3);
		}

		return convertView;
	}

	// TODO almost identical code as AbstractNetworkTask.doInBackground()
	// method.
	@Override
	protected ServerResponse<? extends CountedResponseList<IAnnouncement>> executeRestRequest(
			final int position) throws ParseException,
			ServiceConnectionException
	{
		ServerResponse<? extends CountedResponseList<IAnnouncement>> response = null;
		try
		{
			response = httpTask.execute(FETCH_SIZE, position);
		} catch (final ParseException pe)
		{
			Log.d("AbstractNetwTask", UNABLE_TO_PARSE_RESULT, pe);
			errorResponse = new ErrorResponse();
			errorResponse.setMessage(UNABLE_TO_PARSE_RESULT);
			throw pe;
		} catch (final ServiceConnectionException sce)
		{
			Log.d("AbstractNetwTask",
					MessageFormat.format(UNABLE_TO_CONNECT, httpTask.getUrl()),
					sce);
			errorResponse = new ErrorResponse();
			errorResponse.setMessage(MessageFormat.format(UNABLE_TO_CONNECT,
					httpTask.getUrl()));
			throw sce;
		}
		return response;
	}

}
