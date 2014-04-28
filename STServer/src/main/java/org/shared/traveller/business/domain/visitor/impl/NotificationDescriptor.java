package org.shared.traveller.business.domain.visitor.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.visitor.INotificationVisitor;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class is used to prepare a suitable descriptive message
 * for the notifications that are being processed by it.
 *
 * @author "Ivan Ivanov"
 *
 */
public class NotificationDescriptor implements INotificationVisitor
{
	private final String template;

	private String description;

	/**
	 * Creates a new notification descriptor instance
	 *
	 * @param inTemplate the template used to prepare the notification's
	 * description message. It may not be null.
	 *
	 */
	public NotificationDescriptor(final String inTemplate)
	{
		InstanceAsserter.assertNotNull(inTemplate, "template");

		template = inTemplate;
	}

	/**
	 * Returns the description that has been prepared from the last notification
	 * processing
	 *
	 * @return the description that has been prepared from the last notification
	 *         processing
	 */
	public String getDescription()
	{
		return description;
	}

	@Override
	public void visit(final IPersistentNotification inNotification)
	{
		final String sender = inNotification.getSender().getUsername();
		final IPersistentAnnouncement announcement =
				inNotification.getAnnouncement();
		final String startPoint = announcement.getStartPoint().getName();
		final String endPoint = announcement.getEndPoint().getName();
		final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		final String departureDate = formatter.format(
				announcement.getDepartureDate());

		description = MessageFormat.format(template, sender,
				startPoint, endPoint, departureDate);
	}
}
