package org.ai.shared.traveller.factory.parser;

import org.ai.shared.traveller.network.connection.response.IResponseParserOptions;

/**
 * The interface represents the common functionality for factory classes used
 * for creation of response parsers' options
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IParserOptionsFactory
{
	/**
	 * Creates a new options item for a server response parser
	 * 
	 * @return a new options item for a server response parser
	 */
	IResponseParserOptions createOptions();
}
