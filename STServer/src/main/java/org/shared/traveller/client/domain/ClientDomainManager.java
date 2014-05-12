package org.shared.traveller.client.domain;

import java.io.Serializable;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.ai.shared.traveller.client.factory.builder.rest.RestBuilderFactory;
import org.ai.shared.traveller.client.factory.rest.RestDomainFactory;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class manages the client domains in the server application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class ClientDomainManager implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -2064896993471924354L;

	/**
	 * The enumeration represents the different possible client domains used in
	 * the server application
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	private enum Domain
	{
		/**
		 * The REST client domain
		 */
		REST(new RestDomainFactory(), new RestBuilderFactory());

		private IDomainFactory domainFactory;

		private IBuilderFactory builderFactory;

		/**
		 * Creates a new client domain
		 * 
		 * @param inDomainFactory
		 *            the factory for creating domain instances directly. It may
		 *            not be null
		 * @param inBuilderFactory
		 *            the factory for creating builders for complex domain
		 *            instances. It may not be null
		 */
		private Domain(final IDomainFactory inDomainFactory,
				final IBuilderFactory inBuilderFactory)
		{
			InstanceAsserter.assertNotNull(inDomainFactory, "domain factory");
			InstanceAsserter.assertNotNull(inBuilderFactory, "builder factory");

			domainFactory = inDomainFactory;
			builderFactory = inBuilderFactory;
		}
	}

	private static final ClientDomainManager INSTANCE = new ClientDomainManager(
			Domain.REST);

	private final transient Domain selectedDomain;

	/**
	 * The constructor instantiates a new client domain manager
	 * 
	 * @param inSelectedDomain
	 *            the domain which the new manager represents
	 */
	private ClientDomainManager(final Domain inSelectedDomain)
	{
		selectedDomain = inSelectedDomain;
	}

	/**
	 * The method returns the domain factory associated with the current client
	 * domain manager
	 * 
	 * @return the domain factory associated with the current client domain
	 *         manager
	 */
	public IDomainFactory getDomainFactory()
	{
		return selectedDomain.domainFactory;
	}

	/**
	 * The method returns the builder factory associated with the current client
	 * domain manager
	 * 
	 * @return the builder factory associated with the current client domain
	 *         manager
	 */
	public IBuilderFactory getBuilderFactory()
	{
		return selectedDomain.builderFactory;
	}

	/**
	 * Returns an instance of the client domain manager
	 * 
	 * @return an instance of the client domain manager
	 */
	public static ClientDomainManager getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The method is used to replace any instances coming from the serialization
	 * stream with the one instance of the manager
	 * 
	 * @return the client domain manager instance that is used in the
	 *         application
	 */
	private Object readResolve()
	{
		return INSTANCE;
	}
}
