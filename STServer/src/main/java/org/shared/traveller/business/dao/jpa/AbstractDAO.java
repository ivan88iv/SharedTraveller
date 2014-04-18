package org.shared.traveller.business.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.shared.traveller.business.dao.IDAO;
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

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public T findById(final long inId)
	{
		// TODO handle exceptions
		return entityManager.find(getEntityClass(), Long.valueOf(inId));
	}

	@Transactional
	@Override
	public void persist(final T inInstance)
	{
		assert null != inInstance : NULL_INSTANCE;

		// TODO handle exceptions
		entityManager.persist(inInstance);
	}

	@Transactional
	@Override
	public void merge(T inInstance)
	{
		assert null != inInstance;

		// TODO handle exceptions
		entityManager.merge(inInstance);
	}

	protected abstract Class<? extends T> getEntityClass();
}
