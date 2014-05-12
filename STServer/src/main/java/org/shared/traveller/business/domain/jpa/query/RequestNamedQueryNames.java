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
	 * The name of the query that extracts all generic notifications from the
	 * persistent layer
	 */
	public static final String GET_ALL_GENERIC_NOTIFICATIONS =
			"GenericNotification.getAll";

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

	/**
	 * The name of the query that loads the notifications for a specified
	 * receiver
	 */
	public static final String LOAD_USER_NOTIFICATIONS =
			"Notification.loadUserNotifications";

	/**
	 * The name of the query used for drivers' notifications deletion
	 */
	public static final String REMOVE_DRIVER_NOTIFICATIONS =
			"Notifications.deleteDriverNotifications";

	/**
	 * The name of the query used for passengers' notifications deletion
	 */
	public static final String REMOVE_PASSENGER_NOTIFICATIONS =
			"Notifications.deletePassengerNotifications";

	/**
	 * The name of the query for finding a specific request instance
	 */
	public static final String FIND_REQUEST = "Request.find";

	/**
	 * The name of the query for finding announcements with request information
	 */
	public static final String FIND_ANNOUNCEMENT_WITH_REQUESTS =
			"Announcement.findAnnouncement";
}
