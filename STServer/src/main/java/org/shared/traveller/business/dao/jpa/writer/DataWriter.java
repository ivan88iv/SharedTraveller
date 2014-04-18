package org.shared.traveller.business.dao.jpa.writer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.shared.traveller.business.exception.persistence.DataModificationException;

/**
 * The class manages data modification operations
 * in the database.
 *
 * @author "Ivan Ivanov"
 *
 */
public abstract class DataWriter
{
	private static final String NULL_MANAGER =
			"The entity manager may not be null";

	/**
	 * The method executes the DML query on the database
	 * signified by the given entity manage
	 *
	 * @param inQueryName the name of the query to be executed
	 * @param inEntityManager the entity manager that provides
	 * the db connection
	 * @param inErrorMsg the error message which is to be printed
	 * in case a problem occurs during the operation
	 * @return the number of affected rows by this DML operation
	 *
	 * @throws DataModificationException if a problem occurs while
	 * trying to execute the DML operation
	 */
	public int execute(final String inQueryName,
			final EntityManager inEntityManager,
			final String inErrorMsg) {

		assert null != inEntityManager : NULL_MANAGER;

		final Query query =
				inEntityManager.createNamedQuery(inQueryName);
		prepareQuery(query);

		int affectedRows = 0;
		try
		{
			affectedRows = query.executeUpdate();
		} catch(final PersistenceException ex) {
			throw new DataModificationException(inErrorMsg, ex);
		}

		return affectedRows;
	}

	/**
	 * The method prepares the query to be executed
	 *
	 * @param inQuery the query to be executed
	 */
	protected abstract void prepareQuery(final Query inQuery);
}
