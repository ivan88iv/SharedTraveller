package org.ai.shared.traveller.command.request;

import org.shared.traveller.client.domain.request.IRequestInfo;

/**
 * The interface should be implemented by instances sending new travel requests
 * to the server
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface INewRequestCommand
{
	/**
	 * The method sends the specified request to the server
	 * 
	 * @param inRequest
	 *            the request to be sent
	 */
	void sendRequest(final IRequestInfo inRequest);
}
