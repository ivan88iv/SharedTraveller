package org.shared.traveller.business.dao;

import java.io.Serializable;

import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;

/**
 * The interface represents the common behaviour shared between all DAO
 * instances
 *
 * @author "Ivan Ivanov"
 *
 * @param <T>
 *            the type of the instance accessed
 */
public interface IDAO<T> extends Serializable
{
	/**
	 * The method finds and returns the instance that has the given id
	 *
	 * @param inId
	 *            the id of the instance that is searched.
	 *
	 * @return returns the instance that has the given id
	 *
	 * @throws DataExtractionException
	 *             when a problem occurs while trying to find the instance with
	 *             the specified id
	 */
	T findById(final long inId);

	/**
	 * Saves the provided instance in the data source if it has not been saved
	 * before
	 *
	 * @param inInstance
	 *            the instance to be saved. It must not be null.
	 *
	 * @throws DataModificationException
	 *             when a problem occurs while trying to persist the instance
	 */
	void insert(final T inInstance);

	/**
	 * The method updates the instance if the instance exists in the persistent
	 * layer or saves the new data to the persistent layer otherwise
	 *
	 * @param inInstance
	 *            the instance to be modified/saved
	 *
	 * @throws DataModificationException
	 *             when a problem occurs while trying to update the information
	 *             about the specified instance
	 */
	void update(final T inInstance);
}
