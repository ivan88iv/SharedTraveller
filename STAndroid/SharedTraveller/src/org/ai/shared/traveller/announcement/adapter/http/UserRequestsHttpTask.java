package org.ai.shared.traveller.announcement.adapter.http;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.path.resolver.PathResolver;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.rest.domain.CountedResponseList;
import org.shared.traveller.rest.domain.RequestList;
import org.shared.traveller.rest.param.ParamNames;

import android.app.Activity;

public class UserRequestsHttpTask
		implements
		IAdapterHttpTask<ServerResponse<? extends CountedResponseList<IRequestInfo>>>
{

	private static final String URL_AMPERSAND_SEPARATOR = "&";
	private static final String URL_EQUALS_SEPARATOR = "=";
	private static final String URL = "request/users?";

	private final ServerResponseParser<RequestList> parser = new ServerResponseParser<RequestList>(
			RequestList.class);

	private String url;

	private Activity activity;

	public UserRequestsHttpTask(Activity activity)
	{
		super();
		this.activity = activity;
	}

	@Override
	public ServerResponse<RequestList> execute(int fetchSize,
			int position)
			throws ParseException, ServiceConnectionException
	{
		final IServiceClientFactory clientFactory =
				DomainManager.getInstance().getServiceClientFactory();
		final String servicePath = buildUrl(fetchSize, position);
		final IServiceClient client =
				clientFactory.createSimpleClient(activity, servicePath);

		return client.callService(parser);
	}

	private String buildUrl(int fetchSize, int position)
	{
		final PathResolver pathResolver = new PathResolver(activity);
		final StringBuilder builder = new StringBuilder(
				pathResolver.resolvePath(URL));
		builder.append(ParamNames.START).append(URL_EQUALS_SEPARATOR)
				.append(position).append(URL_AMPERSAND_SEPARATOR)
				.append(ParamNames.COUNT).append(URL_EQUALS_SEPARATOR)
				.append(fetchSize);
		return builder.toString();
	}

	@Override
	public String getUrl()
	{
		return url;
	}

}
