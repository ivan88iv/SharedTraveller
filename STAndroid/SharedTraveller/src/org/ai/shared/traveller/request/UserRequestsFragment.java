package org.ai.shared.traveller.request;

import java.util.ArrayList;

import org.ai.shared.traveller.announcement.adapter.MyRequestsLazyLoadingAdapter;
import org.ai.shared.traveller.announcement.adapter.http.UserRequestsHttpTask;
import org.ai.shared.traveller.list.swipe.AbstractSwipeListFragment;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.request.IRequestInfo;

import android.widget.BaseAdapter;

public class UserRequestsFragment extends
		AbstractSwipeListFragment<IRequestInfo>
{

	@Override
	protected int loadSwipeContainerLayoutId()
	{
		return R.layout.swipe_list_view_fragment;
	}

	@Override
	protected int loadSwipeViewId()
	{
		return R.id.swipe_list_view;
	}

	@Override
	protected BaseAdapter loadAdapter()
	{
		return new MyRequestsLazyLoadingAdapter(getActivity(),
				new ArrayList<IRequestInfo>(), new UserRequestsHttpTask(
						getActivity()));
	}

}
