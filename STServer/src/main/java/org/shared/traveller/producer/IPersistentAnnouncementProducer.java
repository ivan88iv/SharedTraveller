package org.shared.traveller.producer;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.client.domain.visitor.IAnnouncementVisitor;

public interface IPersistentAnnouncementProducer extends IAnnouncementVisitor
{
	IPersistentAnnouncement produce();
}
