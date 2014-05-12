package org.shared.traveller.business.domain.visitor;

import org.shared.traveller.business.domain.IPersistentNotification;

/**
 * The interface represents the common functionality for
 * instance visitors of persistent notifications
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentNotificationVisitor
{
	/**
	 * The method visits the specified persistent notification
	 * and adds dynamically some functionality to it
	 *
	 * @param inNotification the persistent notification to be visited
	 */
	void visit(final IPersistentNotification inNotification);
}
