package org.shared.traveller.business.domain.visitor;

import org.shared.traveller.business.domain.AbstractEntity;

public interface IEntityVisitor
{
	void visit(final AbstractEntity inEntity);
}
