package org.ai.shared.traveller.announcement.adapter.http;

import org.ai.shared.traveller.exceptions.ParseException;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;

public interface IAdapterHttpTask<T>
{
	public T execute(int fetchSize, int position) throws ParseException, ServiceConnectionException;

	public String getUrl();
}
