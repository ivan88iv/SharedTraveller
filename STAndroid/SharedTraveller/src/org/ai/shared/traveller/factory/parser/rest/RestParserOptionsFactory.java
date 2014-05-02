package org.ai.shared.traveller.factory.parser.rest;

import org.ai.shared.traveller.factory.parser.IParserOptionsFactory;
import org.ai.shared.traveller.network.connection.response.IResponseParserOptions;
import org.ai.shared.traveller.network.connection.response.rest.RestResponseParserOptions;

/**
 * The class represents a concrete REST implementation of the factory for
 * creating response parser options
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestParserOptionsFactory implements IParserOptionsFactory
{
	@Override
	public IResponseParserOptions createOptions()
	{
		return new RestResponseParserOptions();
	}
}
