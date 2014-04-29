package org.shared.traveller.business.domain.jpa.query;

public abstract class RequestNamedQueryNames
{
	public static final String GET_REQUESTS_FOR_USER = "Request.get.for.user";

	public static final String GET_REQUESTS_COUNT_FOR_USER =
			"Request.get.count.for.user";

	/**
	 * The name of the query for finding a notification description template by
	 * the notification's type
	 */
	public static final String FIND_NOTIFICATION_TEMPLATE_BY_TYPE =
			"GenericNotification.findByType";

	/**
	 * The name of the query that loads all of the requests for a particular
	 * announcement
	 */
	public static final String LOAD_ANNOUNCEMENT_REQUESTS =
			"Request.loadAnnouncementRequests";

	/**
	 * The name of the query that loads an announcement (with its requests) by
	 * its id
	 */
	public static final String LOAD_ANNOUNCEMENT_WITH_REQUESTS =
			"Announcement.loadAnnouncementWithRequests";
}
