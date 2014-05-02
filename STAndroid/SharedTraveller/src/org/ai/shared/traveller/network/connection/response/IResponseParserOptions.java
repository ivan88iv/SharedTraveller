package org.ai.shared.traveller.network.connection.response;

import com.fasterxml.jackson.databind.Module;

/**
 * The interface represents the common functionality for a response parser's
 * options
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IResponseParserOptions
{
	/**
	 * Returns the the module that holds the options for a response parser
	 * 
	 * @return the the module that holds the options for a response parser
	 */
	Module getTypeMappings();
}
