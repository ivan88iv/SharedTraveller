package org.shared.traveller.business.dao.jpa.extractor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.shared.traveller.business.exception.persistence.DataExtractionException;

/**
 * The class is used for extracting database information safely
 * 
 * @author "Ivan Ivanov"
 * 
 * @param <T>
 */
public abstract class DataExtractor<T>
{
	private static final String NULL_MANAGER = "The entity manager may not be null";

	/**
	 * The method executes the given query and returns a non-null list of the
	 * given type
	 * 
	 * @param inQueryName
	 *            the name of the query to be executed. It must be a select
	 *            query.
	 * @param inEntityManager
	 *            the entity manager used for the database extraction operation
	 * @param inClazz
	 *            the type of the data to be extracted
	 * @param inErrorMsg
	 *            the error message thrown in case of an error
	 * @return a non-null list of the extracted results
	 * 
	 * @throws DataExtractionException
	 *             if a problem occurs while trying to extract the information
	 */
	public List<T> execute(final String inQueryName, final EntityManager inEntityManager, final Class<T> inClazz,
			final String inErrorMsg)
	{
		assert null != inEntityManager : NULL_MANAGER;

		final TypedQuery<T> query = inEntityManager.createNamedQuery(inQueryName, inClazz);
		prepareQuery(query);

		List<T> resultList = null;
		try
		{
			resultList = query.getResultList();

		} catch (PersistenceException ex)
		{
			throw new DataExtractionException(inErrorMsg, ex);
		}

		if (resultList == null)
		{
			resultList = new ArrayList<>();
		}

		return resultList;
	}

	/**
	 * The method prepares the query to be executed
	 * 
	 * @param inQuery
	 *            the query to be executed
	 */
	protected abstract void prepareQuery(final TypedQuery<T> inQuery);
}
