package org.ai.shared.traveller.request;

import java.util.ArrayList;
import java.util.List;

import org.ai.shared.traveller.dialog.STDialog;
import org.ai.shared.traveller.dialog.request.AcceptRequestDialogFactory;
import org.ai.shared.traveller.dialog.request.RejectRequestDialogFactory;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.DeepCopier;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestsAdapter extends ArrayAdapter<IRequestInfo>
{
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

    private List<IRequestInfo> requests = new ArrayList<IRequestInfo>();

    private final IRequestSelectionListener requestSelectionListener;

    public RequestsAdapter(final Context inContext,
            final int inLayoutId,
            final IRequestSelectionListener inRequestSelectionListener)
    {
        super(inContext, inLayoutId, new ArrayList<IRequestInfo>());

        layoutId = inLayoutId;
        requestSelectionListener = inRequestSelectionListener;
    }

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
        attachBtnListeners(holder, currRequest);
        showAppropriateStatus(holder, currRequest);
        holder.usernameHodler.setText(currRequest.getSender());

        return inConvertView;
    }

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

    private void showAppropriateStatus(final ViewHolder inHolder,
            final IRequestInfo inRequest)
    {
        switch (inRequest.getStatus())
        {
        case PENDING:
            applyDrawable(inHolder, R.drawable.pending_request);
            break;
        case APPROVED:
            applyDrawable(inHolder, R.drawable.approved_request);
            break;
        case REJECTED:
            applyDrawable(inHolder, R.drawable.rejected_request);
            break;
        default:
            applyDrawable(inHolder, R.drawable.declined_request);
            break;
        }
    }

    private void attachBtnListeners(final ViewHolder inHolder,
            final IRequestInfo inRequest)
    {
        if (!Status.ACTIVE.equals(inRequest.getAnnouncementStatus()))
        {
            preparePastTravelRequest(inHolder, inRequest);
        } else
        {
            prepareFutureTravelRequest(inHolder, inRequest);
        }

        inHolder.callBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                // TODO implement calling functionality
            }
        });
    }

    private void prepareFutureTravelRequest(final ViewHolder inHolder,
            final IRequestInfo inRequest)
    {
        inHolder.rateBtn.setVisibility(View.GONE);
        inHolder.btnPlaceholder.setVisibility(View.GONE);

        final RequestStatus requestStatus = inRequest.getStatus();
        if (RequestStatus.PENDING.equals(requestStatus))
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
                    requestSelectionListener.onRequestSelect(
                            inRequest.getId());
                    STDialog.show(factory);
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
                    STDialog.show(factory);
                }
            });
        } else
        {
            inHolder.acceptBtn.setEnabled(false);
            inHolder.rejectBtn.setEnabled(false);
        }
    }

    private void preparePastTravelRequest(final ViewHolder inHolder,
            final IRequestInfo inRequest)
    {
        inHolder.acceptBtn.setVisibility(View.GONE);
        inHolder.rejectBtn.setVisibility(View.GONE);
        if (Status.COMPLETED.equals(inRequest.getAnnouncementStatus()) &&
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

    private void applyDrawable(final ViewHolder inHolder,
            final int drawableId)
    {
        inHolder.statusHolder.setImageDrawable(
                getContext().getResources().getDrawable(
                        drawableId));
    }
}
