package org.shared.traveller.business.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.client.domain.INotification.Type;

@Entity(name = "generic_notification")
@Table(name = "generic_notification")
@NamedQueries(
{
		@NamedQuery(name =
				RequestNamedQueryNames.FIND_NOTIFICATION_TEMPLATE_BY_TYPE,
				query = "SELECT gn.template FROM generic_notification gn "
						+ "WHERE gn.type = :type")
})
public class GenericNotificationEntity extends AbstractEntity
		implements IPersistentGenericNotification
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -5601586387248538707L;

	@Id
	@GeneratedValue
	@Column(name = "ID", updatable = false)
	private Long id;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "TEMPLATE", nullable = false)
	private String template;

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public String getTemplate()
	{
		return template;
	}
}
