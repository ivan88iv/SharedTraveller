package org.ai.shared.traveller.request;

import org.ai.shared.traveller.command.request.IRequestExtractionCommand;
import org.ai.shared.traveller.list.swipe.AbstractSwipeListFragment;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;

import android.app.Activity;
import android.widget.ArrayAdapter;

public class AnnouncementRequestsFragment
        extends AbstractSwipeListFragment<IRequestInfo>
{
    private IRequestExtractionCommand requestCommand;

    private IAnnouncement announcement;

    private IRequestSelectionListener requestSelectionListener;

    public static AnnouncementRequestsFragment newInstance(
            final IRequestExtractionCommand inCommand,
            final IAnnouncement inAnnouncement)
    {
        final AnnouncementRequestsFragment fragment =
                new AnnouncementRequestsFragment();
        fragment.requestCommand = inCommand;
        fragment.announcement = inAnnouncement;
        return fragment;
    }

    @Override
    public void onAttach(final Activity activity)
    {
        super.onAttach(activity);
        requestSelectionListener = (IRequestSelectionListener) activity;
    }

    @Override
    protected int loadSwipeContainerLayoutId()
    {
        return R.layout.announcement_requests;
    }

    @Override
    protected int loadSwipeViewId()
    {
        return R.id.requests_swipe_view;
    }

    @Override
    protected ArrayAdapter<IRequestInfo> loadAdapter()
    {
        final RequestsAdapter adapter = new RequestsAdapter(
                getActivity(), R.layout.requests_list,
                requestSelectionListener);
        requestCommand.extractRequests(announcement, adapter);
        return adapter;
    }
}
