package org.shared.traveller.business.domain;

import java.io.Serializable;

/**
 * The interface represents some common functionality for all persistent
 * instances in the application
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IPersistent extends Serializable
{
	/**
	 * Returns the indentificator of the current persistent instance
	 *
	 * @return the indentificator of the current persistent instance
	 */
	Long getId();
}
