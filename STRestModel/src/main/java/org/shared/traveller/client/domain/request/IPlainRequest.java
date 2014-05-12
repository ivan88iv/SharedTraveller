package org.shared.traveller.client.domain.request;

import java.io.Serializable;

import org.shared.traveller.client.domain.traveller.INotificationTraveller;

/**
 * The interface represents a plain request instance that has no information
 * about the announcement it is related to
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IPlainRequest extends Serializable
{
	/**
	 * Returns the id of the request
	 * 
	 * @return the id of the request
	 */
	Long getId();

	/**
	 * Returns the status of the request
	 * 
	 * @return the status of the request
	 */
	RequestStatus getStatus();

	/**
	 * Returns information about the sender of the request
	 * 
	 * @return information about the sender of the request
	 */
	INotificationTraveller getSender();
}
