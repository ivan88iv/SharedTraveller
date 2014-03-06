package org.shared.traveller.business.domain.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.utility.DeepCopier;

@Entity(name = "notification")
@Table(name = "notification")
public class NotificationEntity extends AbstractEntity implements
		IPersistentNotification
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -5587121471588199943L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "NOTIFICATION_TEXT")
	private String text;

	@Column(name = "SEEN", nullable = false)
	private Boolean seen;

	@ManyToOne
	@JoinColumn(name = "TRAVELLER_ID")
	private TravellerEntity traveller;

	public static class BusinessNotificationBuilder
	{
		private final Long idField;

		private final Date creationDateField;

		private String textField;

		private Boolean seenField;

		private final TravellerEntity travellerField;

		public BusinessNotificationBuilder(final Long inId,
				final Date inCreationDate, final TravellerEntity inTraveller)
		{
			idField = inId;
			creationDateField = DeepCopier.copy(inCreationDate);
			seenField = false;
			// TODO deep copy
			travellerField = inTraveller;
		}

		public BusinessNotificationBuilder text(final String inText)
		{
			textField = inText;
			return this;
		}

		public BusinessNotificationBuilder seen(final Boolean inIsSeen)
		{
			seenField = inIsSeen;
			return this;
		}

		public NotificationEntity build()
		{
			return new NotificationEntity(this);
		}
	}

	protected NotificationEntity()
	{
		// used for JPA purposes
	}

	private NotificationEntity(final BusinessNotificationBuilder inBuilder)
	{
		id = inBuilder.idField;
		creationDate = DeepCopier.copy(inBuilder.creationDateField);
		text = inBuilder.textField;
		seen = inBuilder.seenField;
		// TODO deep copy
		traveller = inBuilder.travellerField;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public TravellerEntity getTraveller()
	{
		// TODO deep copy
		return traveller;
	}

	@Override
	public Date getCreationDate()
	{
		return DeepCopier.copy(creationDate);
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public Boolean getSeen()
	{
		return seen;
	}
}
