package org.shared.traveller.business.domain;

import org.shared.traveller.client.domain.request.RequestStatus;

/**
 * The interface represents the functionality a persistent request
 * should possess
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentRequest
{
	/**
	 * Returns the identification of the request
	 *
	 * @return the identification of the request
	 */
	Long getId();

	/**
	 * Returns the sender of the request
	 *
	 * @return the sender of the request
	 */
	IPersistentTraveller getSender();

	/**
	 * Returns the announcement the request has been sent about
	 *
	 * @return the announcement the request has been sent about
	 */
	IPersistentAnnouncement getAnnouncement();

	/**
	 * Returns the status of the request
	 *
	 * @return the status of the request
	 */
	RequestStatus getStatus();

	/**
	 * The method sets the new status of a request
	 *
	 * @param inStatus the new status to be set
	 */
	void setStatus(final RequestStatus inStatus);
}
