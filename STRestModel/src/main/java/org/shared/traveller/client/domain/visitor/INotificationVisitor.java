package org.shared.traveller.client.domain.visitor;

import org.shared.traveller.client.domain.INotification;

/**
 * The interfaces allows dynamically adding of functionality to a notification
 * instance
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface INotificationVisitor
{
	/**
	 * Visits the specified notification instance and adds some behaviour
	 * dynamically
	 * 
	 * @param inNotification
	 *            the notification that is visited. It may not be null
	 */
	void visit(final INotification inNotification);
}
