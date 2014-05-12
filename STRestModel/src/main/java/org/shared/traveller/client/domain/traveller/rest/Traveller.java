package org.shared.traveller.client.domain.traveller.rest;

import org.shared.traveller.client.domain.traveller.ITraveller;

/**
 * The class represents a traveller REST domain object
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class Traveller implements ITraveller
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 4653940767797622949L;

	private final String username;

	/**
	 * Instantiates a new REST traveller
	 * 
	 * @param inUsername
	 *            the user name of the traveller
	 */
	public Traveller(final String inUsername)
	{
		username = inUsername;
	}

	@Override
	public String getUsername()
	{
		return username;
	}
}
