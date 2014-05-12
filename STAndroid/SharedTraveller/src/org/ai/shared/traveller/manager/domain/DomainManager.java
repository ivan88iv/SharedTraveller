package org.ai.shared.traveller.manager.domain;

import java.io.Serializable;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.ai.shared.traveller.client.factory.builder.rest.RestBuilderFactory;
import org.ai.shared.traveller.client.factory.rest.RestDomainFactory;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.factory.client.rest.RestServiceClientFactory;
import org.ai.shared.traveller.factory.parser.IParserOptionsFactory;
import org.ai.shared.traveller.factory.parser.rest.RestParserOptionsFactory;

/**
 * The class is used to provide information required about the current domain
 * the application is using
 * 
 * @author "Ivan Ivanov"
 * 
 */
public final class DomainManager implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 4129202025424129967L;

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
		REST(new RestDomainFactory(), new RestBuilderFactory(),
				new RestServiceClientFactory(),
				new RestParserOptionsFactory());

		private final IDomainFactory domainFactory;

		private final IBuilderFactory builderFactory;

		private final IServiceClientFactory clientFactory;

		private final IParserOptionsFactory parserOptionsFactory;

		/**
		 * Creates a new application domain
		 * 
		 * @param inDomainFactory
		 *            the factory used for creating simple client domain
		 *            instances
		 * @param inBuilderFactory
		 *            the factory instance used for creating domain builder
		 *            instances
		 * @param inClientFactory
		 *            the factory instance used for creating service client
		 *            instances
		 * @param inOptionsFactory
		 *            the factory instance used for creating options for server
		 *            response parsers
		 */
		private Domain(final IDomainFactory inDomainFactory,
				final IBuilderFactory inBuilderFactory,
				final IServiceClientFactory inClientFactory,
				final IParserOptionsFactory inOptionsFactory)
		{
			domainFactory = inDomainFactory;
			builderFactory = inBuilderFactory;
			clientFactory = inClientFactory;
			parserOptionsFactory = inOptionsFactory;
		}
	}

	private final static DomainManager INSTANCE = new DomainManager();

	private transient final Domain selectedDomain = Domain.REST;

	/**
	 * This class is not meant to be instantiated from outer classes. There is a
	 * special utility method that should be used for this purpose
	 */
	private DomainManager()
	{
	}

	/**
	 * The method returns the factory used for simple client domain instances
	 * creation
	 * 
	 * @return the factory used for simple client domain instances creation
	 */
	public IDomainFactory getDomainFactory()
	{
		return selectedDomain.domainFactory;
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
	 * Returns the factory instance responsible for creating the option items
	 * for server response parsers
	 * 
	 * @return the factory instance responsible for creating the option items
	 *         for server response parsers
	 */
	public IParserOptionsFactory getParserOptionsFactory()
	{
		return selectedDomain.parserOptionsFactory;
	}

	/**
	 * Returns the domains manager for the application
	 * 
	 * @return the domains manager for the application
	 */
	public static DomainManager getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The method is used to replace any instances coming from the serialization
	 * stream with the one instance of the manager
	 * 
	 * @return the domain manager instance that is used in the application
	 */
	private Object readResolve()
	{
		return INSTANCE;
	}
}