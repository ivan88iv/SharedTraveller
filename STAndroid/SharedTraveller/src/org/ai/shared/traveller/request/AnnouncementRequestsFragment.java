package org.ai.shared.traveller.request;

import org.ai.shared.traveller.call.CallerActivity;
import org.ai.shared.traveller.command.request.IRequestExtractionCommand;
import org.ai.shared.traveller.list.swipe.AbstractSwipeListFragment;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.request.IRequestInfo;

import android.app.Activity;

/**
 * The class represents the visual state for the travel requests screen
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class AnnouncementRequestsFragment
		extends AbstractSwipeListFragment<IRequestInfo, RequestsAdapter>
{
	private IRequestExtractionCommand requestCommand;

	private IAnnouncement announcement;

	private IRequestSelectionListener requestSelectionListener;

	/**
	 * Instantiates a new fragment responsible for showing the travel requests
	 * 
	 * @param inCommand
	 *            the command for extracting travel requests from the server
	 *            side
	 * @param inAnnouncement
	 *            the announcement for which requests are to be extracted
	 * @return the newly created fragment
	 */
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
	protected RequestsAdapter loadAdapter()
	{
		final RequestsAdapter adapter = new RequestsAdapter(
				(CallerActivity) getActivity(),
				R.layout.requests_list, announcement,
				requestSelectionListener);
		requestCommand.extractRequests(announcement, adapter);
		return adapter;
	}
}
