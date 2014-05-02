package org.shared.traveller.client.domain;

import java.io.Serializable;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.ai.shared.traveller.client.factory.rest.RestDomainFactory;

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
		REST(new RestDomainFactory());

		private IDomainFactory domainFactory;

		private Domain(final IDomainFactory inDomainFactory)
		{
			domainFactory = inDomainFactory;
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
