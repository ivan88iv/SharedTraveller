package org.shared.traveller.business.dao;

import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.client.domain.INotification.Type;

/**
 * The class represents a DAO for accessing generic notificaiton instances from
 * the persistent layer
 *
 * @author "Ivan Ivanov"
 *
 */
public interface IGenericNotificationDAO
		extends IDAO<IPersistentGenericNotification>
{
	/**
	 * The method retrieves the description template for the specified
	 * notification's type
	 *
	 * @param inType
	 *            the type for which a description template is extracted. It may
	 *            not be null.
	 * @return the extracted notification template or an empty string if none is
	 *         found
	 *
	 * @throws DataExtractionException
	 *            if a problem occurs while trying to find the template
	 */
	String findNotificationTemplate(final Type inType);
}
