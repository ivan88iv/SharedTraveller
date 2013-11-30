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
import org.shared.traveller.rest.domain.AnnouncementsList;

public class AnnouncementListHttpTask implements IAdapterHttpTask<ServerResponse<AnnouncementsList>>
{

	private static final String URL_AMPERSAND_SEPARATOR = "&";
	private static final String URL = "stserver/dummy/getAnouncments?";

	private final ServerResponseParser<AnnouncementsList> parser = new ServerResponseParser<AnnouncementsList>(AnnouncementsList.class);

	private final AbstractRestClient restClient;

	private String from;

	private String to;

	private String date;

	private String url;

	public AnnouncementListHttpTask()
	{
		super();
		restClient = new SimpleClient(RequestTypes.GET);
	}

	public AnnouncementListHttpTask(String from, String to, String date)
	{
		this();
		this.from = from;
		this.to = to;
		this.date = date;
	}

	@Override
	public ServerResponse<AnnouncementsList> execute(int fetchSize, int position) throws ParseException, ServiceConnectionException
	{
		ServerResponse<AnnouncementsList> response = null;
		PathResolver pathResolver = new PathResolver();
		url = appednFilter(pathResolver.resolvePath(URL), fetchSize, position);
		try
		{
			response = restClient.callService(new URL(url), parser);
		}
		catch (MalformedURLException e)
		{
			throw new IllegalUrlException(url, e);
		}

		return response;
	}

	@Override
	public String getUrl()
	{
		return url;
	}

	private String appednFilter(String url, int fetchSize, int position)
	{
		StringBuilder builder = new StringBuilder(url);
		builder.append("start=" + position).append(URL_AMPERSAND_SEPARATOR).append("count=" + fetchSize);
		if (from != null && from.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append("from=" + from);
		}
		if (to != null && to.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append("to=" + to);
		}
		if (date != null && date.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append("date=" + date);
		}
		return builder.toString();
	}
}
