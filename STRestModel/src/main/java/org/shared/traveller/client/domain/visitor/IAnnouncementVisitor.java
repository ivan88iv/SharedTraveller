package org.shared.traveller.client.domain.visitor;

import org.shared.traveller.client.domain.IAnnouncement;

public interface IAnnouncementVisitor
{
	void visit(final IAnnouncement inRestObject);
}
