package org.shared.traveller.business.dao;

import java.io.Serializable;

public interface IDAO<T> extends Serializable
{
	T findById(final Class<T> inClass, final long inId);

	void persist(final T inInstance);
}
