package org.ai.shared.traveller.client.factory.rest;

import java.util.Date;

import org.ai.shared.traveller.client.factory.IDomainFactory;
import org.shared.traveller.client.domain.INotification;
import org.shared.traveller.client.domain.INotification.Type;
import org.shared.traveller.client.domain.rest.Notification;

/**
 * The class represents a REST factory for creating simple client instances
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class RestDomainFactory implements IDomainFactory
{
	@Override
	public INotification createNotification(Type inType, Date inCreationDate,
			String inDescription)
	{
		return new Notification(inType, inCreationDate, inDescription);
	}
}
