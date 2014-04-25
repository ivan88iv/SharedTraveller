package org.ai.shared.traveller.announcement.adapter.http;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.response.ServerResponse;
import org.ai.shared.traveller.network.connection.response.ServerResponseParser;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.rest.domain.AnnouncementsList;
import org.shared.traveller.rest.domain.CountedResponseList;
import org.shared.traveller.rest.param.ParamNames;
import org.shared.traveller.rest.param.SortOrder;

import android.app.Activity;

public class AnnouncementListHttpTask implementspublic class AnnouncementListHttpTask implements
		IAdapterHttpTask<ServerResponse<AnnouncementsList>>{
	private static final String URL_AMPERSAND_SEPARATOR = "&";
	private static final String URL_EQUALS_SEPARATOR = "=";
	private static final String URL = "announcement/all?";

	private final ServerResponseParser<AnnouncementsList> parser = new ServerResponseParser<AnnouncementsList>(
			AnnouncementsList.class);

	private String from;

	private String to;

	private String date;

	private String url;

	private SortOrder sortOrder;

	private final Activity activity;

	public AnnouncementListHttpTask(final Activity inActivity)
	{
		super();
		activity = inActivity;
	}

	public AnnouncementListHttpTask(final Activity inActivity,
			final String from, final String to, final String date)
	{
		this(inActivity);
		this.from = from;
		this.to = to;
		this.date = date;
	}

	public AnnouncementListHttpTask(final Activity inActivity,
			final String from, final String to, final String date,
			final SortOrder sortOrder)
	{
		this(inActivity);
		this.from = from;
		this.to = to;
		this.date = date;
		this.sortOrder = sortOrder;
	}

	@Override
	public ServerResponse<AnnouncementsList> execute(final int fetchSize,
			final int position) throws ParseException,
			ServiceConnectionException
	{
		ServerResponse<AnnouncementsList> response = null;
		url = appednFilter(URL, fetchSize, position);
		final IServiceClientFactory clientFactory =
				DomainManager.getInstance().getServiceClientFactory();
		final IServiceClient client = clientFactory.createSimpleClient(
				activity,
				url);
		response = client.callService(parser);

    @Override
    public ServerResponse<? extends CountedResponseList<IAnnouncement>> execute(final int fetchSize,
            final int position) throws ParseException,
            ServiceConnectionException
    {
        ServerResponse<AnnouncementsList> response = null;
        final PathResolver pathResolver = new PathResolver(activity);
        url = appednFilter(pathResolver.resolvePath(URL), fetchSize, position);
        try
        {
            response = restClient.callService(new URL(url), parser);
        } catch (final MalformedURLException e)
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

	private String appednFilter(final String url, final int fetchSize,
			final int position)
	{
		final StringBuilder builder = new StringBuilder(url);
		builder.append(ParamNames.START).append(URL_EQUALS_SEPARATOR)
				.append(position).append(URL_AMPERSAND_SEPARATOR)
				.append(ParamNames.COUNT).append(URL_EQUALS_SEPARATOR)
				.append(fetchSize);
		if (from != null && from.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.FROM)
					.append(URL_EQUALS_SEPARATOR).append(from);
		}
		if (to != null && to.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.TO)
					.append(URL_EQUALS_SEPARATOR).append(to);
		}
		if (date != null && date.length() > 0)
		{
			builder.append(URL_AMPERSAND_SEPARATOR).append(ParamNames.DATE)
					.append(URL_EQUALS_SEPARATOR).append(date);
		}
		if (sortOrder != null)
		{
			builder.append(URL_AMPERSAND_SEPARATOR)
					.append(ParamNames.SORT_ORDER).append(URL_EQUALS_SEPARATOR)
					.append(sortOrder);
		}
		return builder.toString();
	}
}
