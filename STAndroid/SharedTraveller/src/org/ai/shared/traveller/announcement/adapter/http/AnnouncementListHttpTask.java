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
import org.shared.traveller.rest.param.ParamNames;
import org.shared.traveller.rest.param.SortOrder;

public class AnnouncementListHttpTask implements IAdapterHttpTask<ServerResponse<AnnouncementsList>>
{

	private static final String URL_AMPERSAND_SEPARATOR = "&";
	private static final String URL_EQUALS_SEPARATOR = "=";
	private static final String URL = "stserver/dummy/getAnouncments?";

	private final ServerResponseParser<AnnouncementsList> parser = new ServerResponseParser<AnnouncementsList>(AnnouncementsList.class);

	private final AbstractRestClient restClient;

	private String from;

	private String to;

	private String date;

	private String url;

	private SortOrder sortOrder;

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

	public AnnouncementListHttpTask(String from, String to, String date, SortOrder sortOrder)
	{
		this();
		this.from = from;
		this.to = to;
		this.date = date;
		this.sortOrder = sortOrder;
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
		builder.append(ParamNames.START).append(URL_EQUALS_SEPARATOR).append(position).append(URL_AMPERSAND_SEPARATOR).append(ParamNames.COUNT).append(URL_EQUALS_SEPARATOR).append(fetchSize);
		if (from != null && from.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.FROM).append(URL_EQUALS_SEPARATOR).append(from);
		}
		if (to != null && to.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.TO).append(URL_EQUALS_SEPARATOR).append(to);
		}
		if (date != null && date.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.DATE).append(URL_EQUALS_SEPARATOR).append(date);
		}
		if (sortOrder != null)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.SORT_ORDER).append(URL_EQUALS_SEPARATOR).append(sortOrder);
		}
		return builder.toString();
	}
}