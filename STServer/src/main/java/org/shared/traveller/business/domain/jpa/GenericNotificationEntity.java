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
						+ "WHERE gn.type = :type"),
		@NamedQuery(name =
				RequestNamedQueryNames.GET_ALL_GENERIC_NOTIFICATIONS,
				query = "SELECT gn FROM generic_notification gn")
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

	@Column(name = "SINGULAR_TITLE")
	private String singularTitle;

	@Column(name = "PLURAL_TITLE")
	private String pluralTitle;

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

	@Override
	public String getSingularTitle()
	{
		return singularTitle;
	}

	@Override
	public String getPluralTitle()
	{
		return pluralTitle;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("------------------ Generi Notification -----------\n");
		builder.append("id: ").append(id).append("\n");
		builder.append("type: ").append(type).append("\n");
		builder.append("template: ").append(template).append("\n");
		builder.append("singular title: ").append(singularTitle).append("\n");
		builder.append("plural title: ").append(pluralTitle).append("\n");
		builder.append("--------------------------------------------------\n");

		return builder.toString();
	}
}
