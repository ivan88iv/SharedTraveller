package org.shared.traveller.business.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.shared.traveller.business.dao.IDAO;

public class AbstractDAO<T> implements IDAO<T>
{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -5446099310378382001L;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public T findById(final Class<T> inClass, final long inId)
	{
		// TODO handle exceptions
		return entityManager.find(inClass, Long.valueOf(inId));
	}

	@Override
	public void persist(final T inInstance)
	{
		// TODO handle exceptions
		entityManager.persist(inInstance);
	}
}
