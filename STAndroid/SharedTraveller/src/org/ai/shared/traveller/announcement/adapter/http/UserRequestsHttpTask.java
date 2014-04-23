package org.ai.shared.traveller.announcement.adapter.http;

import java.net.MalformedURLException;
import java.net.URL;

import org.ai.shared.traveller.exceptions.IllegalUrlException;
import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.path.resolver.PathResolver;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.ai.shared.traveller.network.connection.rest.client.AbstractRestClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
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

	private final AbstractRestClient restClient;

	private String url;

	private Activity activity;

	public UserRequestsHttpTask(Activity activity)
	{
		super();
		this.activity = activity;
		restClient = new SimpleClient(RequestTypes.GET);
	}

	@Override
	public ServerResponse<RequestList> execute(int fetchSize,
			int position)
			throws ParseException, ServiceConnectionException
	{
		String url = buildUrl(fetchSize, position);
		ServerResponse<RequestList> response = null;
		try
		{
			response = restClient.callService(new URL(url), parser);
		} catch (final MalformedURLException e)
		{
			throw new IllegalUrlException(url, e);
		}

		return response;
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
