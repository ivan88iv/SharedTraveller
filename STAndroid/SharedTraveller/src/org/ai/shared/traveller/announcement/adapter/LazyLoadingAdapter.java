package org.ai.shared.traveller.announcement.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.sharedtraveller.R;
import org.shared.traveller.rest.domain.CountedResponseList;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.commonsware.cwac.endless.EndlessAdapter;

/**
 * Lazy loading announcement adapter. Loads 50 items chunks only if already
 * loaded items end is reached.
 * 
 * @author AlexanderIvanov
 * 
 */
public abstract class LazyLoadingAdapter<T> extends EndlessAdapter
{

	protected static final int FETCH_SIZE = 50;
	protected static final String UNABLE_TO_PARSE_RESULT = "Unable to parse result from a service call";
	protected static final String UNABLE_TO_CONNECT = "Could not connect to service {0}.";

	private RotateAnimation rotate = null;
	private View pendingView = null;

	private final AtomicInteger count = new AtomicInteger(0);
	private List<T> chunk = new ArrayList<T>();
	protected ErrorResponse errorResponse;

	public LazyLoadingAdapter(
			final Context ctxt,
			final ArrayList<T> list)
	{
		super(ctxt, new ArrayAdapter<T>(ctxt,
				R.layout.swipe_list_view_row, android.R.id.text1, list));
		this.rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		this.rotate.setDuration(600);
		this.rotate.setRepeatMode(Animation.RESTART);
		this.rotate.setRepeatCount(Animation.INFINITE);
	}

	protected abstract ServerResponse<? extends CountedResponseList<T>> executeRestRequest(
			final int position) throws ParseException,
			ServiceConnectionException;

	@Override
	protected View getPendingView(final ViewGroup parent)
	{
		final View row = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.swipe_list_view_row, null);

		pendingView = row.findViewById(R.id.throbber);
		pendingView.setVisibility(View.VISIBLE);
		startProgressAnimation();

		return (row);
	}

	@Override
	protected boolean onException(final View pendingView, final Exception e)
	{
		// TODO create user notification for exception
		if (errorResponse != null)
		{
			Toast.makeText(getContext(), errorResponse.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	@Override
	protected boolean executeInBackground(final int position)
			throws ServiceConnectionException, ParseException
	{
		final ServerResponse<? extends CountedResponseList<T>> response = executeRestRequest(position);

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

	private void resetAdapterData()
	{
		count.set(-1);
		chunk = new ArrayList<T>();
	}

	@Override
	protected void appendData()
	{
		if (getWrappedAdapter().getCount() < count.get())
		{
			@SuppressWarnings("unchecked")
			final ArrayAdapter<T> a =
					(ArrayAdapter<T>) getWrappedAdapter();

			for (final T an : chunk)
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