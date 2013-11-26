package org.ai.shared.traveller.announcement.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.ai.shared.traveller.exceptions.IllegalUrlException;
import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.path.resolver.PathResolver;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.domain.Announcement;
import org.shared.traveller.rest.domain.AnnouncementsList;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * Lazy loading announcement adapter. Loads 50 items chunks only if already
 * loaded items end is reached.
 * 
 * @author AlexanderIvanov
 * 
 */
public class AnnouncementLazyLoadingAdapter extends EndlessAdapter
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

	private static final String URL = "stserver/dummy/getAnouncments/{0}/{1}";
	private static final int FETCH_SIZE = 50;
	private static final String UNABLE_TO_PARSE_RESULT = "Unable to parse result from a service call";

	private static final String UNABLE_TO_CONNECT = "Could not connect to service {0}.";

	private RotateAnimation rotate = null;
	private View pendingView = null;

	private AtomicInteger count = new AtomicInteger(-1);
	private List<Announcement> chunk = new ArrayList<Announcement>();
	private ErrorResponse errorResponse;
	private final AbstractRestClient restClient;
	private final ServerResponseParser<AnnouncementsList> parser = new ServerResponseParser<AnnouncementsList>(AnnouncementsList.class);

	public AnnouncementLazyLoadingAdapter(Context ctxt, ArrayList<Announcement> list)
	{
		super(ctxt, new ArrayAdapter<Announcement>(ctxt, R.layout.swipe_list_view_row, android.R.id.text1, list));
		rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(600);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);
		restClient = new SimpleClient(RequestTypes.GET);
	}

	@Override
	protected View getPendingView(ViewGroup parent)
	{
		View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_list_view_row, null);

		pendingView = row.findViewById(R.id.throbber);
		pendingView.setVisibility(View.VISIBLE);
		startProgressAnimation();

		return (row);
	}

	@Override
	protected View getDataView(int position, View convertView, ViewGroup parent)
	{
		final Announcement item = (Announcement) getItem(position);

		if (item != null)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = li.inflate(R.layout.swipe_list_view_row, parent, false);
				holder = new ViewHolder();
				holder.container = (LinearLayout) convertView.findViewById(R.id.announcement_container);
				holder.from = (TextView) convertView.findViewById(R.id.from);
				holder.to = (TextView) convertView.findViewById(R.id.to);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.bAction1 = (Button) convertView.findViewById(R.id.row_b_action_1);
				holder.bAction2 = (Button) convertView.findViewById(R.id.row_b_action_2);
				holder.bAction3 = (Button) convertView.findViewById(R.id.row_b_action_3);
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
			holder.date.setText(item.getDate().toString());
		}

		return convertView;
	}

	@Override
	protected boolean onException(View pendingView, Exception e)
	{
		// TODO create user notification for exception
		if (errorResponse != null)
		{
			Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	protected boolean executeInBackground(int position) throws ServiceConnectionException, ParseException
	{
		ServerResponse<AnnouncementsList> response = executeRestRequest(position);

		// if exception is throws the response will be null. ResponseError will
		// be handled in onException() method
		if (response != null)
		{
			count.set(response.getResponseBody().getCount());
			chunk = response.getResponseBody().getList();
		}
		else
		{
			// If the response is null exceptional condition has occurred. The
			// collection containing the last retrieved chunk is cleared to
			// be avoided data duplication
			resetAdapterData();
		}

		return getWrappedAdapter().getCount() < count.get();
	}

	//TODO almost identical code as AbstractNetworkTask.doInBackground() method.
	private ServerResponse<AnnouncementsList> executeRestRequest(int position) throws ParseException, ServiceConnectionException
	{
		ServerResponse<AnnouncementsList> response = null;
		PathResolver pathResolver = new PathResolver();
		String url = MessageFormat.format(pathResolver.resolvePath(URL), position, FETCH_SIZE);
		try
		{
			try
			{
				response = restClient.callService(new URL(url), parser);
			}
			catch (MalformedURLException e)
			{
				throw new IllegalUrlException(url, e);
			}

		}
		catch (final ParseException pe)
		{
			Log.d("AbstractNetwTask", UNABLE_TO_PARSE_RESULT, pe);
			errorResponse = new ErrorResponse();
			errorResponse.setMessage(UNABLE_TO_PARSE_RESULT);
			throw pe;
		}
		catch (final ServiceConnectionException sce)
		{
			Log.d("AbstractNetwTask", MessageFormat.format(UNABLE_TO_CONNECT, url), sce);
			errorResponse = new ErrorResponse();
			errorResponse.setMessage(MessageFormat.format(UNABLE_TO_CONNECT, url));
			throw sce;
		}
		return response;
	}

	private void resetAdapterData()
	{
		count.set(-1);
		chunk = new ArrayList<Announcement>();
	}

	@Override
	protected void appendData()
	{
		if (getWrappedAdapter().getCount() < count.get())
		{
			@SuppressWarnings("unchecked")
			ArrayAdapter<Announcement> a = (ArrayAdapter<Announcement>) getWrappedAdapter();

			for (Announcement an : chunk)
			{
				a.add(an);
			}
		}
	}

	void startProgressAnimation()
	{
		if (pendingView != null)
		{
			pendingView.startAnimation(rotate);
		}
	}

}