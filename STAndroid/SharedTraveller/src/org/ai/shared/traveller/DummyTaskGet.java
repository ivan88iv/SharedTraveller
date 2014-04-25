package org.ai.shared.traveller;

import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.shared.traveller.rest.domain.DummyRequest;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;

public class DummyTaskGet extends
		AbstractNetworkTask<MainActivity, DummyRequest>
{

	public DummyTaskGet(final MainActivity inActivity,
			final IServiceClient inClient)
	{
		super(inActivity, inClient, DummyRequest.class);
	}

	@Override
	protected void onSuccess(final DummyRequest inResult)
	{
		Log.i("DummyTaskGet", "SUCCESS: " + inResult);
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.i("DummyTaskGet", "ERROR: " + inStatusCode + "  " + inError);
	}
}
