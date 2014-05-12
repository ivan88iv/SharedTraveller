package org.shared.traveller.business.domain;

import org.shared.traveller.client.domain.INotification.Type;

/**
 * The interface represents a persistent notification template instance
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IPersistentGenericNotification extends IPersistent
{
	/**
	 * Returns the type of the generic notification
	 * 
	 * @return the type of the generic notification
	 */
	Type getType();

	/**
	 * Returns the template for this type of notification
	 * 
	 * @return the template for this type of notification
	 */
	String getTemplate();

	/**
	 * The method returns the singular title for the generic notification
	 * 
	 * @return the singular title for the generic notification
	 */
	String getSingularTitle();

	/**
	 * The method returns the plural title for the generic notification
	 * 
	 * @return the plural title for the generic notification
	 */
	String getPluralTitle();
}
