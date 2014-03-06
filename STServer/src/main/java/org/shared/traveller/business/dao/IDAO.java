package org.shared.traveller.business.dao;

import java.io.Serializable;

/**
 * The interface represents the common behaviour shared between
 * all DAO instances
 *
 * @author "Ivan Ivanov"
 *
 * @param <T> the type of the instance accessed
 */
public interface IDAO<T> extends Serializable
{
	/**
	 * The method finds and returns the instance that has
	 * the given id
	 *
	 * @param inId the id of the instance that is searched.
	 *
	 * @return returns the instance that has the given id
	 */
	T findById(final long inId);

	/**
	 * Saves the provided instance in the data source
	 *
	 * @param inInstance the instance to be saved. It must not be null.
	 */
	void persist(final T inInstance);
}
