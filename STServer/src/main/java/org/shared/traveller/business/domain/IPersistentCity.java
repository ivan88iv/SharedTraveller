package org.shared.traveller.business.domain;

import org.shared.traveller.business.domain.jpa.CountryEntity;

/**
 * The interface represents a persistent city instance
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistentCity extends IPersistent
{
	/**
	 * Returns the name of the city
	 *
	 * @return the name of the city
	 */
	String getName();

	/**
	 * Returns the country in which the city is
	 *
	 * @return the country in which the city is
	 */
	CountryEntity getCountry();
}
