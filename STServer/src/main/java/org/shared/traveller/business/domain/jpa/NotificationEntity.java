package org.shared.traveller.business.domain.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.shared.traveller.business.domain.AbstractEntity;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.IPersistentTraveller;
import org.shared.traveller.business.domain.jpa.query.RequestNamedQueryNames;
import org.shared.traveller.business.domain.visitor.IPersistentNotificationVisitor;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.DeepCopier;
import org.shared.traveller.utility.InstanceAsserter;

@Entity(name = "Notification")
@Table(name = "notification")
@NamedQueries(
{
		@NamedQuery(name = RequestNamedQueryNames.LOAD_USER_NOTIFICATIONS,
				query = "SELECT n FROM Notification n "
						+ "WHERE n.receiver.id = :receiverId "
						+ "ORDER BY n.creationDate ASC"),
		@NamedQuery(name = RequestNamedQueryNames.REMOVE_DRIVER_NOTIFICATIONS,
				query = "DELETE FROM Notification n "
						+ "WHERE n.receiver.id = :driverId AND "
						+ "n.announcement.id = :announcementId AND "
						+ "(n.type = org.shared.traveller.client.domain.INotification$Type.NEW_REQUEST OR "
						+ "n.type = org.shared.traveller.client.domain.INotification$Type.REQUEST_DECLINATION)"),
		@NamedQuery(name = RequestNamedQueryNames.REMOVE_PASSENGER_NOTIFICATIONS,
				query = "DELETE FROM Notification n "
						+ "WHERE n.receiver.id = :passengerId AND "
						+ "(n.type = org.shared.traveller.client.domain.INotification$Type.REQUEST_REJECTION OR "
						+ "n.type = org.shared.traveller.client.domain.INotification$Type.REQUEST_ACCEPTANCE OR "
						+ "n.type = org.shared.traveller.client.domain.INotification$Type.TRIP_CANCELLATION)")
})
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

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToOne
	@JoinColumn(name = "RECEIVER_ID", nullable = false)
	private TravellerEntity receiver;

	@ManyToOne
	@JoinColumn(name = "SENDER_ID", nullable = false)
	private TravellerEntity sender;

	@ManyToOne
	@JoinColumn(name = "ANNOUNCEMENT_ID", nullable = false)
	private AnnouncementEntity announcement;

	public static class BusinessNotificationBuilder
			implements IPersistentNotificationBuilder
	{
		private Long idField;

		private final Date creationDateField;

		private final Type typeField;

		private final TravellerEntity receiverField;

		private final TravellerEntity senderField;

		private final AnnouncementEntity announcementField;

		public BusinessNotificationBuilder(final Type inType,
				final TravellerEntity inReceiver,
				final TravellerEntity inSender,
				final AnnouncementEntity inAnnouncement,
				final Date inCreationDate)
		{
			InstanceAsserter.assertNotNull(inType, "type");
			InstanceAsserter.assertNotNull(inReceiver, "receiver");
			InstanceAsserter.assertNotNull(inSender, "sender");
			InstanceAsserter.assertNotNull(inCreationDate, "creation date");

			typeField = inType;
			receiverField = new TravellerEntity(inReceiver);
			senderField = new TravellerEntity(inSender);
			creationDateField = DeepCopier.copy(inCreationDate);
			announcementField = new AnnouncementEntity(inAnnouncement);
		}

		@Override
		public IPersistentNotificationBuilder id(final Long inId)
		{
			idField = inId;
			return this;
		}

		@Override
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
		type = inBuilder.typeField;
		receiver = new TravellerEntity(inBuilder.receiverField);
		sender = new TravellerEntity(inBuilder.senderField);
		announcement = new AnnouncementEntity(inBuilder.announcementField);
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public TravellerEntity getReceiver()
	{
		return new TravellerEntity(receiver);
	}

	@Override
	public IPersistentTraveller getSender()
	{
		return new TravellerEntity(sender);
	}

	@Override
	public Date getCreationDate()
	{
		return DeepCopier.copy(creationDate);
	}

	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public IPersistentAnnouncement getAnnouncement()
	{
		return new AnnouncementEntity(announcement);
	}

	@Override
	public void accept(IPersistentNotificationVisitor inVisitor)
	{
		InstanceAsserter.assertNotNull(inVisitor, "visitor");

		inVisitor.visit(this);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("-------------- Notification ---------------\n");
		builder.append("id: ").append(id).append("\n");
		builder.append("creation date: ").append(creationDate).append("\n");
		builder.append("type: ").append(type).append("\n");
		builder.append("receiver: ").append(receiver).append("\n");
		builder.append("sender: ").append(sender).append("\n");
		builder.append("announcement: ").append(announcement).append("\n");
		builder.append("-------------------------------------------\n");
		return builder.toString();
	}
}
