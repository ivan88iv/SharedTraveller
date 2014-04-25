package org.ai.shared.traveller.manager.domain;

import org.ai.shared.traveller.factory.builder.IBuilderFactory;
import org.ai.shared.traveller.factory.builder.rest.RestBuilderFactory;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.factory.client.rest.RestServiceClientFactory;

/**
 * The class is used to provide information required about the current domain
 * the application is using
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class DomainManager
{
	/**
	 * The enumeration represents the different domains available for this
	 * application
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	private enum Domain
	{
		/**
		 * The REST domain
		 */
		REST(new RestBuilderFactory(), new RestServiceClientFactory());

		private final IBuilderFactory builderFactory;

		private final IServiceClientFactory clientFactory;

		/**
		 * Creates a new application domain
		 * 
		 * @param inBuilderFactory
		 *            the factory instance used for creating domain builder
		 *            instances
		 * @param inClientFactory
		 *            the factory instance used for creating service client
		 *            instances
		 */
		private Domain(final IBuilderFactory inBuilderFactory,
				final IServiceClientFactory inClientFactory)
		{
			builderFactory = inBuilderFactory;
			clientFactory = inClientFactory;
		}
	}

	private final Domain selectedDomain = Domain.REST;

	/**
	 * This class is not meant to be instantiated from outer classes. There is a
	 * special utility method that should be used for this purpose
	 */
	private DomainManager()
	{

	}

	/**
	 * Returns the factory used for creating domain builder classes
	 * 
	 * @return the factory used for creating domain builder classes
	 */
	public IBuilderFactory getBuilderFactory()
	{
		return selectedDomain.builderFactory;
	}

	/**
	 * Returns the factory used for creating service clients
	 * 
	 * @return the factory used for creating service clients
	 */
	public IServiceClientFactory getServiceClientFactory()
	{
		return selectedDomain.clientFactory;
	}

	/**
	 * Returns the domains manager for the application
	 * 
	 * @return the domains manager for the application
	 */
	public static DomainManager getInstance()
	{
		return new DomainManager();
	}
}
