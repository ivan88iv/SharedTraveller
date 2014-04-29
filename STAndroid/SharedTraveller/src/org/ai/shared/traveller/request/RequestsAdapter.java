package org.ai.shared.traveller.request;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.call.CallerActivity;
import org.ai.shared.traveller.dialog.STDialogFragment;
import org.ai.shared.traveller.dialog.request.AcceptRequestDialogFactory;
import org.ai.shared.traveller.dialog.request.RejectRequestDialogFactory;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.DeepCopier;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The class represents an adapter that is responsible for preparing requests'
 * views in the application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RequestsAdapter extends ArrayAdapter<IRequestInfo>
{
	/**
	 * The class represents a holder of the visual resources for a single row
	 * inside the adapter
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	private static class ViewHolder
	{
		private Button acceptBtn;

		private Button rejectBtn;

		private Button callBtn;

		private Button rateBtn;

		private TextView btnPlaceholder;

		private TextView usernameHodler;

		private ImageView statusHolder;
	}

	private final int layoutId;

	private IAnnouncement announcement;

	private List<IRequestInfo> requests = new ArrayList<IRequestInfo>();

	private final IRequestSelectionListener requestSelectionListener;

	/**
	 * Instantiates a new requests adapter
	 * 
	 * @param inActivity
	 *            the caller activity which is responsible for performing the
	 *            calls to the requests in the adapter
	 * @param inLayoutId
	 *            the id of the used layout
	 * @param inAnnouncement
	 *            the announcement for which the requests are visualized
	 * @param inRequestSelectionListener
	 *            the listener of selections of the requests represented by the
	 *            adapter
	 */
	public RequestsAdapter(final CallerActivity inActivity,
			final int inLayoutId, final IAnnouncement inAnnouncement,
			final IRequestSelectionListener inRequestSelectionListener)
	{
		super(inActivity, inLayoutId, new ArrayList<IRequestInfo>());

		layoutId = inLayoutId;
		announcement = inAnnouncement;
		requestSelectionListener = inRequestSelectionListener;
	}

	/**
	 * The method renews the list of requests to be shown
	 * 
	 * @param inRequests
	 *            the requests to be displayed in the view that the current
	 *            adapter is responsible for
	 */
	public void setRequests(final List<IRequestInfo> inRequests)
	{
		clear();
		if (inRequests == null)
		{
			requests = new ArrayList<IRequestInfo>();
		} else
		{
			requests = DeepCopier.copy(inRequests);
			for (final IRequestInfo info : inRequests)
			{
				add(info);
			}
		}
	}

	/**
	 * Returns the announcement for which requests are represented by this
	 * adapter
	 * 
	 * @return the announcement for which requests are represented by this
	 *         adapter
	 */
	public IAnnouncement getSelectedAnnouncement()
	{
		return announcement;
	}

	/**
	 * Sets the new announcement for the adapter instance
	 * 
	 * @param inAnnouncement
	 *            the new announcement for which requests are represented by
	 *            this adapter
	 */
	public void setSelectedAnnouncement(final IAnnouncement inAnnouncement)
	{
		announcement = inAnnouncement;
	}

	@Override
	public View getView(final int inPosition, View inConvertView,
			final ViewGroup inParent)
	{
		boolean loadNewHolder = false;

		if (null == inConvertView)
		{
			final LayoutInflater inflater =
					((Activity) getContext()).getLayoutInflater();
			inConvertView = inflater.inflate(layoutId, inParent, false);
			loadNewHolder = true;
		}

		final ViewHolder holder = loadViewHolder(inConvertView, inParent,
				loadNewHolder);
		final IRequestInfo currRequest = requests.get(inPosition);
		attachBtnListeners(inPosition, holder, currRequest);
		showAppropriateStatus(holder, currRequest);
		holder.usernameHodler.setText(currRequest.getSender());

		return inConvertView;
	}

	/**
	 * The method loads a view holder element with the information about the
	 * specified row
	 * 
	 * @param inRow
	 *            the row for which a view holder element is loaded
	 * @param inParent
	 *            the parent of the current row view
	 * @param inLoadNewHolder
	 *            if true a new view holder is created. Else a view holder is
	 *            loaded from the row's tag attribute
	 * @return the loaded view holder
	 */
	private ViewHolder loadViewHolder(final View inRow,
			final ViewGroup inParent, final boolean inLoadNewHolder)
	{
		ViewHolder holder = null;

		if (inLoadNewHolder)
		{
			holder = new ViewHolder();
			holder.acceptBtn = (Button)
					inRow.findViewById(R.id.accept_request_btn);
			holder.callBtn = (Button)
					inRow.findViewById(R.id.call_request_btn);
			holder.rateBtn = (Button)
					inRow.findViewById(R.id.rate_request_btn);
			holder.rejectBtn = (Button)
					inRow.findViewById(R.id.reject_request_btn);
			holder.btnPlaceholder = (TextView)
					inRow.findViewById(R.id.requests_btn_placeholder);
			holder.usernameHodler = (TextView)
					inRow.findViewById(R.id.request_sender_holder);
			holder.statusHolder = (ImageView)
					inRow.findViewById(R.id.request_status_icon);
			inRow.setTag(holder);
		} else
		{
			holder = (ViewHolder) inRow.getTag();
		}

		return holder;
	}

	/**
	 * The method visualizes the appropriate status icon for the given view
	 * holder depending on the request information which it should represent
	 * 
	 * @param inHolder
	 *            the view holder that should visualize the request information
	 * @param inRequest
	 *            the request information to be visualized
	 */
	private void showAppropriateStatus(final ViewHolder inHolder,
			final IRequestInfo inRequest)
	{
		switch (inRequest.getStatus())
		{
		case PENDING:
			applyStatusDrawable(inHolder, R.drawable.pending_request);
			break;
		case APPROVED:
			applyStatusDrawable(inHolder, R.drawable.approved_request);
			break;
		case REJECTED:
			applyStatusDrawable(inHolder, R.drawable.rejected_request);
			break;
		default:
			applyStatusDrawable(inHolder, R.drawable.declined_request);
			break;
		}
	}

	/**
	 * The method attaches the appropriate button listeners to the specified
	 * view holder's buttons
	 * 
	 * @param inPosition
	 *            the row position for which the specified view holder is
	 *            responsible
	 * @param inHolder
	 *            the view holder keeping the information for the specified row
	 * @param inRequest
	 *            the request information to be displayed
	 */
	private void attachBtnListeners(final int inPosition,
			final ViewHolder inHolder,
			final IRequestInfo inRequest)
	{
		if (Status.ACTIVE.equals(announcement.getStatus()))
		{
			prepareFutureTravelRequest(inPosition, inHolder, inRequest);
		} else
		{
			preparePastTravelRequest(inHolder, inRequest);
		}

		inHolder.callBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final CallerActivity caller = (CallerActivity) getContext();
				caller.call(inRequest.getDriverPhone());
			}
		});
	}

	/**
	 * The method prepares a request view holder for a travel which has not
	 * started yet
	 * 
	 * @param inPosition
	 *            the row position for the request in the view
	 * @param inHolder
	 *            the view holder with the visual elements for the specified row
	 * @param inRequest
	 *            the request information to be applied
	 */
	private void prepareFutureTravelRequest(final int inPosition,
			final ViewHolder inHolder, final IRequestInfo inRequest)
	{
		inHolder.rateBtn.setVisibility(View.GONE);
		inHolder.btnPlaceholder.setVisibility(View.GONE);

		final RequestStatus requestStatus = inRequest.getStatus();
		if (RequestStatus.PENDING.equals(requestStatus) &&
				announcement.getSeats() > 0)
		{
			inHolder.acceptBtn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View v)
				{
					final AcceptRequestDialogFactory factory =
							new AcceptRequestDialogFactory(
									(FragmentActivity) getContext(),
									inRequest.getSender());
					requestSelectionListener.onRequestSelect(inPosition);
					STDialogFragment.show(factory);
				}
			});
			inHolder.rejectBtn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View v)
				{
					final RejectRequestDialogFactory factory =
							new RejectRequestDialogFactory(
									(FragmentActivity) getContext(),
									inRequest.getSender());
					requestSelectionListener.onRequestSelect(inPosition);
					STDialogFragment.show(factory);
				}
			});
		} else
		{
			inHolder.acceptBtn.setEnabled(false);
			inHolder.rejectBtn.setEnabled(false);
		}
	}

	/**
	 * The method prepares the request view holder for a travel announcement
	 * which is currently no longer active
	 * 
	 * @param inHolder
	 *            the view holder to be prepared
	 * @param inRequest
	 *            the request for which the view holder is prepared
	 */
	private void preparePastTravelRequest(final ViewHolder inHolder,
			final IRequestInfo inRequest)
	{
		inHolder.acceptBtn.setVisibility(View.GONE);
		inHolder.rejectBtn.setVisibility(View.GONE);
		if (Status.COMPLETED.equals(announcement.getStatus()) &&
				RequestStatus.APPROVED.equals(inRequest.getStatus()))
		{
			inHolder.rateBtn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View v)
				{
					// TODO implement rate functionality
				}
			});
		} else
		{
			inHolder.rateBtn.setEnabled(false);
		}
	}

	/**
	 * The method applies the specified drawable resource representing the
	 * status of the request for which the provided view holder is responsible
	 * 
	 * @param inHolder
	 *            the view holder which is modified
	 * @param drawableId
	 *            the id of the drawable resource to be applied
	 */
	private void applyStatusDrawable(final ViewHolder inHolder,
			final int drawableId)
	{
		inHolder.statusHolder.setImageDrawable(
				getContext().getResources().getDrawable(
						drawableId));
	}
}
