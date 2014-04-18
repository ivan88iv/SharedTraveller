package org.shared.traveller.transformer;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;

/**
 * The class is responsible for transforming a client domain announcement
 * to a persistent announcement
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentAnnouncementProducer extends IAnnouncementVisitor
{
	/**
	 * The method transforms the client domain announcement
	 * to a persistent announcement
	 *
	 * @return the transformed announcement
	 */
	IPersistentAnnouncement produce();
}
