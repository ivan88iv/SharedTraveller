package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.shared.traveller.business.dao.IDAO;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.business.exception.persistence.DataModificationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public abstract class AbstractDAO<T> implements IDAO<T>
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -5446099310378382001L;

	private static final String NULL_INSTANCE =
			"The instance may not be null.";

	private static final String DATA_INSERTION_PROBLEM =
			"A problem occurred while trying to insert the following instance "
					+ "into the persistent layer: {0}";

	private static final String DATA_UPDATE_PROBLEM =
			"A problem occurred while trying to update the following instance "
					+ "from the persistent layer: {0}";

	private static final String DATA_EXTRACTION_PROBLEM =
			"A problem occurred while trying to find information about"
					+ " an instance with identificator {0} and class {1}.";

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public T findById(final long inId)
	{
		try
		{
			return entityManager.find(getEntityClass(), Long.valueOf(inId));
		} catch (final IllegalArgumentException iae)
		{
			throw new DataExtractionException(
					MessageFormat.format(DATA_EXTRACTION_PROBLEM, inId,
							getEntityClass()), iae);
		}
	}

	@Transactional
	@Override
	public void insert(final T inInstance)
	{
		assert null != inInstance : NULL_INSTANCE;

		try
		{
			entityManager.persist(inInstance);
		} catch (final IllegalArgumentException | PersistenceException pe)
		{
			throw new DataModificationException(
					MessageFormat.format(DATA_INSERTION_PROBLEM, inInstance),
					pe);
		}
	}

	@Transactional
	@Override
	public T update(T inInstance)
	{
		assert null != inInstance;

		try
		{
			return entityManager.merge(inInstance);
		} catch (final IllegalArgumentException | PersistenceException pe)
		{
			throw new DataModificationException(
					MessageFormat.format(DATA_UPDATE_PROBLEM, inInstance), pe);
		}
	}

	protected abstract Class<? extends T> getEntityClass();
}
