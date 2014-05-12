package org.ai.shared.traveller.client.factory.builder.rest;

import java.util.Date;

import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.INotification.IBuilder;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.rest.RequestInfo;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;
import org.shared.traveller.client.domain.rest.Notification;
import org.shared.traveller.client.domain.rest.RequestedAnnouncement;
import org.shared.traveller.client.domain.traveller.rest.NotificationTraveller;
import org.shared.traveller.utility.InstanceAsserter;

/**
 * The class represents a factory for REST domain builder classes
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestBuilderFactory implements IBuilderFactory
{
	@Override
	public IAnnouncement.IBuilder createAnnouncementBuilder(
			final String inFrom, final String inTo,
			final Date inDepDate, final short inSeats,
			final String inDriverUsername)
	{
		InstanceAsserter.assertNotNull(inFrom, "from point");
		InstanceAsserter.assertNotNull(inTo, "to point");
		InstanceAsserter.assertNotNull(inDepDate, "departure date");
		InstanceAsserter.assertNotNull(inDriverUsername, "driver's username");

		return new AnnouncementBuilder(inFrom, inTo,
				inDepDate, inSeats, inDriverUsername);
	}

	@Override
	public IRequestInfo.IBuilder createRequestInfoBuilder()
	{
		return new RequestInfo.RequestInfoBuilder();
	}

	@Override
	public IBuilder createNotificationBuilder()
	{
		return new Notification.Builder();
	}

	@Override
	public org.shared.traveller.client.domain.IRequestedAnnouncement.IBuilder
			createRequestedAnnouncementBuilder()
	{
		return new RequestedAnnouncement.Builder();
	}

	@Override
	public org.shared.traveller.client.domain.traveller.INotificationTraveller.IBuilder
			createNotificationTravellerBuilder()
	{
		return new NotificationTraveller.Builder();
	}
}
