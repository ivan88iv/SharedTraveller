package org.shared.traveller.client.domain.request;

/**
 * The enumeration represents the possible request statuses
 * 
 * @author "Ivan Ivanov"
 * 
 */
public enum RequestStatus {

	/**
	 * The request is pending for an approval
	 */
	PENDING,

	/**
	 * The request has been rejected
	 */
	REJECTED,

	/**
	 * The request has been approved
	 */
	APPROVED,

	/**
	 * The sender has declined the request
	 */
	DECLINED
}
