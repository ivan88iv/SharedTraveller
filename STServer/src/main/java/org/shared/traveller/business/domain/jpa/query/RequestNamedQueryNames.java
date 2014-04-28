package org.shared.traveller.business.domain.jpa.query;

public abstract class RequestNamedQueryNames
{
	public static final String GET_REQUESTS_FOR_USER = "Request.get.for.user";

	public static final String GET_REQUESTS_COUNT_FOR_USER =
			"Request.get.count.for.user";

	/**
	 * The name of the query for finding a notification description
	 * template by the notification's type
	 */
	public static final String FIND_NOTIFICATION_TEMPLATE_BY_TYPE =
			"GenericNotification.findByType";
}
