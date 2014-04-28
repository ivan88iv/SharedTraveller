package org.shared.traveller.business.domain;

import org.shared.traveller.business.factory.IBusinessDomainFactory;
import org.shared.traveller.business.factory.builder.IBusinessBuilderFactory;
import org.shared.traveller.business.factory.builder.jpa.JPABuilderFactory;
import org.shared.traveller.business.factory.jpa.JPADomainFactory;

/**
 * The class is responsible for selecting the current business domain that
 * should be used throughout the application
 *
 * @author "Ivan Ivanov"
 *
 */
public final class BusinessDomainManager
{
	/**
	 * The enumeration represents the business domain options from which the
	 * manager selects
	 *
	 * @author "Ivan Ivanov"
	 *
	 */
	private enum BusinessDomain
	{
		/**
		 * The Jave persistence api business domain
		 */
		JPA(new JPABuilderFactory(), new JPADomainFactory());

		private final IBusinessBuilderFactory builderFactory;

		private final IBusinessDomainFactory domainFactory;

		/**
		 * Instantiates a business domain option
		 *
		 * @param inBuilderFactory
		 *            the factory used for creating builder instances from which
		 *            the domain objects can be instantiated
		 * @param inDomainFactory
		 *            the factory used for creating domain specific instances
		 */
		private BusinessDomain(final IBusinessBuilderFactory inBuilderFactory,
				final IBusinessDomainFactory inDomainFactory)
		{
			builderFactory = inBuilderFactory;
			domainFactory = inDomainFactory;
		}
	}

	private final BusinessDomain selectedDomain = BusinessDomain.JPA;

	/**
	 * The class is not meant to be instantiated through this constructor. A
	 * special utility method should be used instead.
	 */
	private BusinessDomainManager()
	{
	}

	/**
	 * Returns the builder factory for the given business domain
	 *
	 * @return the builder factory for the given business domain
	 */
	public IBusinessBuilderFactory getBuilderFactory()
	{
		return selectedDomain.builderFactory;
	}

	/**
	 * Returns the factory used for creating domain instances
	 *
	 * @return the factory used for creating domain instances
	 */
	public IBusinessDomainFactory getDomainFactory()
	{
		return selectedDomain.domainFactory;
	}

	/**
	 * The method is used to instantiate a new business domain manager instance
	 *
	 * @return the new business domain manager instance to be created
	 */
	public static BusinessDomainManager getInstance()
	{
		return new BusinessDomainManager();
	}
}
