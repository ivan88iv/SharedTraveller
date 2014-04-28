package org.shared.traveller.client.domain.visitor;

import org.shared.traveller.client.domain.IAnnouncement;

/**
 * The class is responsible for visiting client domain announcements
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IAnnouncementVisitor
{
	/**
	 * The method visits the specified announcement so that some business
	 * behaviour can be dynamically added to this instance
	 * 
	 * @param inAnnouncement
	 *            the client domain announcement to be visited
	 */
	void visit(final IAnnouncement inAnnouncement);
}
