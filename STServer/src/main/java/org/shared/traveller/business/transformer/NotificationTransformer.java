package org.shared.traveller.business.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.shared.traveller.business.domain.IPersistentGenericNotification;
import org.shared.traveller.business.domain.IPersistentNotification;
import org.shared.traveller.business.domain.visitor.impl.NotificationDescriptor;
import org.shared.traveller.client.domain.ClientDomainManager;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class transforms persistent notifications to their client domain
 * counterparts
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationTransformer
{
	private final IBuilderFactory builderFactory;

	private final Map<Type, IPersistentGenericNotification> genericData;

	/**
	 * Creates a new notification transformer instance
	 * 
	 * @param inGenericNotifications
	 *            generic notifications used in the transformation process. It
	 *            may not be null.
	 */
	public NotificationTransformer(
			final List<? extends IPersistentGenericNotification>
			inGenericNotifications)
	{
		InstanceAsserter.assertNotNull(inGenericNotifications,
				"generic notifications");

		builderFactory = ClientDomainManager.getInstance().getBuilderFactory();
		genericData = prepareGenericData(inGenericNotifications);
	}

	/**
	 * The method transforms the notifications provided eliminating any
	 * notifications from the resultant list.
	 * 
	 * @param inPersistentNotifications
	 *            the notifications to be transformed by the new instance. This
	 *            parameter may not be null
	 * @return the transformed notification instances that are part of the
	 *         client domain
	 */
	public List<? extends INotification> transformNotifications(
			final List<? extends IPersistentNotification>
			inPersistentNotifications)
	{
		InstanceAsserter.assertNotNull(inPersistentNotifications,
				"notifications");

		final List<INotification> clientNotifications = new ArrayList<>();
		final Map<String, Integer> notificationCnts = new HashMap<>();
		final List<IPersistentNotification> accumulativeNotifications =
				new ArrayList<>();

		for (final IPersistentNotification currNot : inPersistentNotifications)
		{
			final Type notificationType = currNot.getType();
			if (isAccumulative(notificationType))
			{
				accumulate(currNot, notificationCnts, accumulativeNotifications);
			} else
			{
				// construct the notification's description
				final NotificationDescriptor descriptor =
						new NotificationDescriptor(
								genericData.get(notificationType));
				final INotification notification = transformNotification(
						currNot, descriptor);
				clientNotifications.add(notification);
			}
		}

		addAccumulatedNotifications(builderFactory, notificationCnts,
				accumulativeNotifications, clientNotifications);

		return clientNotifications;
	}

	/**
	 * The method transforms the passed persistent notification to a client
	 * domain notification instance. The order of the notifications is kept with
	 * accumulated notifications showing only the last simple notification from
	 * the list.
	 * 
	 * @param inNotificationToTransform
	 *            the notification to be transformed. It may not be null.
	 * @param inDescriptor
	 *            the descriptor instance used in the transformation process. It
	 *            may not be null.
	 * @return the transformed notification
	 */
	private INotification transformNotification(
			final IPersistentNotification inNotificationToTransform,
			final NotificationDescriptor inDescriptor)
	{
		inNotificationToTransform.accept(inDescriptor);
		final String description = inDescriptor.getDescription();
		final String title = inDescriptor.getTitle();

		final INotification.IBuilder builder = builderFactory
				.createNotificationBuilder();

		return builder.announcementId(
				inNotificationToTransform.getAnnouncement().getId())
				.creationDate(
						inNotificationToTransform.getCreationDate())
				.description(description)
				.title(title)
				.type(inNotificationToTransform.getType())
				.build();
	}

	/**
	 * The method adds the accumulated notifications to the rest of the
	 * transformed notifications. Only the last notification from an
	 * accumulation type is shown
	 * 
	 * @param inDomainBuilderFactory
	 *            the factory instance used to create the builder for client
	 *            domain notification. It may not be null.
	 * @param inAccumulatedCnts
	 *            a map that holds information about the number of notifications
	 *            for each accumulated notification type. It may not be null
	 * @param inAccumulateNotifs
	 *            the list of the accumulated notifications to be added. It may
	 *            not be null.
	 * @param inTransformedNotifs
	 *            the list of transformed notifications to which the accumulated
	 *            ones are to be added. It may not be null
	 */
	private void addAccumulatedNotifications(
			final IBuilderFactory inDomainBuilderFactory,
			final Map<String, Integer> inAccumulatedCnts,
			final List<IPersistentNotification> inAccumulateNotifs,
			final List<INotification> inTransformedNotifs)
	{
		final int accNotifsCnt = inAccumulateNotifs.size();

		for (int currNotInd = accNotifsCnt - 1; currNotInd >= 0; --currNotInd)
		{
			final IPersistentNotification accNot = inAccumulateNotifs
					.get(currNotInd);
			final Integer count = inAccumulatedCnts.get(
					constructAccumulationKey(accNot));
			final NotificationDescriptor descriptor = new NotificationDescriptor(
					genericData.get(accNot.getType()), count.intValue());

			final INotification notification = transformNotification(
					accNot, descriptor);
			inTransformedNotifs.add(notification);
		}
	}

	/**
	 * The method enlarges the number of notifications from the same accumulated
	 * type in the counts map and adds the notification to the list of
	 * accumulated notifications only if it has not been already added
	 * 
	 * @param inNotification
	 *            the notification instance to be accumulated. It may not be
	 *            null
	 * @param inNotificationCnts
	 *            a map that holds information about the number of notifications
	 *            for each accumulated notification type. It may not be null
	 * @param inAccumulatedNotifications
	 *            the list holds only one instance for each accumulated
	 *            notification. It may not be null
	 */
	private void accumulate(final IPersistentNotification inNotification,
			final Map<String, Integer> inNotificationCnts,
			final List<IPersistentNotification> inAccumulatedNotifications)
	{
		final String accumulationKey = constructAccumulationKey(inNotification);
		final Integer accumulatedCnt = inNotificationCnts.get(accumulationKey);

		if (null == accumulatedCnt)
		{
			inNotificationCnts.put(accumulationKey, 1);
			inAccumulatedNotifications.add(inNotification);
		} else
		{
			inNotificationCnts.put(accumulationKey, accumulatedCnt + 1);
		}
	}

	/**
	 * Constructs a key used for the accumulation notification type to which the
	 * current notification belongs
	 * 
	 * @param inNotification
	 *            the notification for which a key is constructed. It may not be
	 *            null
	 * @return the constructed key
	 */
	private String constructAccumulationKey(
			final IPersistentNotification inNotification)
	{
		return inNotification.getType().toString()
				+ ":" + inNotification.getAnnouncement().getId();
	}

	/**
	 * Returns true if the type represents a notification that can contain
	 * information about several notifications in it
	 * 
	 * @param inType
	 *            the type to be checked
	 * @return true if the type represents a notification that can contain
	 *         information about several notifications in it
	 */
	private boolean isAccumulative(final Type inType)
	{
		return inType == Type.NEW_REQUEST ||
				inType == Type.REQUEST_DECLINATION;
	}

	/**
	 * The method constructs a map of the generic notifications' types and the
	 * generic notifications themselves
	 * 
	 * @param inNotifications
	 *            the generic notifications list to be transformed in a map. It
	 *            may not be null.
	 * @return the created map of generic notification's type - generic
	 *         notification pairs
	 */
	private static Map<Type, IPersistentGenericNotification> prepareGenericData(
			final List<? extends IPersistentGenericNotification> inNotifications)
	{
		final Map<Type, IPersistentGenericNotification> data =
				new HashMap<>();

		for (final IPersistentGenericNotification currNot : inNotifications)
		{
			data.put(currNot.getType(), currNot);
		}

		return data;
	}
}
