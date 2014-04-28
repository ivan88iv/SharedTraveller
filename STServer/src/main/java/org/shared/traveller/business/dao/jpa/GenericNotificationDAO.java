package org.shared.traveller.business.dao.jpa;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.IGenericNotificationDAO;
import org.shared.traveller.business.dao.jpa.extractor.DataExtractor;
import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.domain.jpa.GenericNotificationEntity;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.business.exception.persistence.DataExtractionException;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.InstanceAsserter;
import org.springframework.stereotype.Repository;

/**
 * The class represents a JPA DAO for accessing the generic notifications from
 * the persistent layer
 *
 * @author "Ivan Ivanov"
 *
 */
@Repository
public class GenericNotificationDAO extends
		AbstractDAO<IPersistentGenericNotification>
		implements IGenericNotificationDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1621972695504660618L;

	private static final String TEMPLATE_EXTRACTION_PROBLEM =
			"A problem occurred while trying to find the template for "
					+ "notification's type {0}.";

	/**
	 * @throws DataExtractionException
	 *             - if a problem occurs while trying to extract the information
	 */
	@Override
	public String findNotificationTemplate(final Type inType)
	{
		InstanceAsserter.assertNotNull(inType, "generic notification's type");

		final DataExtractor<String> extractor = new DataExtractor<String>()
		{
			@Override
			protected void prepareQuery(final TypedQuery<String> inQuery)
			{
				inQuery.setParameter("type", inType);
			}
		};

		final List<String> resultList = extractor.execute(
				RequestNamedQueryNames.FIND_NOTIFICATION_TEMPLATE_BY_TYPE,
				entityManager, String.class,
				MessageFormat.format(TEMPLATE_EXTRACTION_PROBLEM, inType));
		String template = "";

		if (!resultList.isEmpty())
		{
			template = resultList.get(0);
		}

		return template;
	}

	@Override
	protected Class<? extends IPersistentGenericNotification> getEntityClass()
	{
		return GenericNotificationEntity.class;
	}
}
