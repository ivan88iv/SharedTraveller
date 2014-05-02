package org.ai.shared.traveller.network.connection.response.rest;

import org.ai.shared.traveller.network.connection.response.IResponseParserOptions;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.rest.Notification;
import org.shared.traveller.client.domain.rest.RequestInfo;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * The class represents the concrete REST implementation of the response
 * parser's options
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestResponseParserOptions implements IResponseParserOptions
{
	private final SimpleModule typeResolver = new SimpleModule();

	/**
	 * Creates a new options instance for the rest server response parser
	 * instances
	 */
	public RestResponseParserOptions()
	{
		prepareTypeMappings();
	}

	@Override
	public SimpleModule getTypeMappings()
	{
		return typeResolver;
	}

	/**
	 * The method prepares the type mappings used by the current options
	 * instance
	 */
	private void prepareTypeMappings()
	{
		typeResolver
				.addAbstractTypeMapping(INotification.class, Notification.class)
				.addAbstractTypeMapping(IRequestInfo.class, RequestInfo.class);
	}
}
