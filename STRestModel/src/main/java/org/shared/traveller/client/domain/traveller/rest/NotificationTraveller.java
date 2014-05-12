package org.shared.traveller.client.domain.traveller.rest;

import org.shared.traveller.client.domain.traveller.INotificationTraveller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a REST domain notification traveller
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NotificationTraveller implements INotificationTraveller
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -9219206476500019670L;

	private final Long id;

	private final String username;

	private final String phoneNumber;

	private final String email;

	private final boolean smsNotificationAllowed;

	private final boolean emailNotificationAllowed;

	/**
	 * The class is used to create a new notification traveller
	 * 
	 * @author "Ivan Ivanov"
	 * 
	 */
	public static class Builder implements IBuilder
	{
		private Long idField;

		private String usernameField;

		private String phoneNumberField;

		private String emailField;

		private boolean allowSmsNotifsField;

		private boolean allowEmailNotifsField;

		@Override
		public IBuilder id(final Long inId)
		{
			idField = inId;
			return this;
		}

		@Override
		public IBuilder username(String inUsername)
		{
			usernameField = inUsername;
			return this;
		}

		@Override
		public IBuilder phoneNumber(String inPhoneNumber)
		{
			phoneNumberField = inPhoneNumber;
			return this;
		}

		@Override
		public IBuilder email(String inEmail)
		{
			emailField = inEmail;
			return this;
		}

		@Override
		public IBuilder allowSmsNotifications(boolean inAllow)
		{
			allowSmsNotifsField = inAllow;
			return this;
		}

		@Override
		public IBuilder allowEmailNotifications(boolean inAllow)
		{
			allowEmailNotifsField = inAllow;
			return this;
		}

		@Override
		public INotificationTraveller build()
		{
			return new NotificationTraveller(this);
		}
	}

	/**
	 * Creates a new notification traveller using the supplied builder
	 * 
	 * @param inBuilder
	 *            the builder used for the traveller creation. It may not be
	 *            null
	 */
	private NotificationTraveller(final Builder inBuilder)
	{
		this(inBuilder.idField, inBuilder.usernameField,
				inBuilder.phoneNumberField, inBuilder.emailField,
				inBuilder.allowSmsNotifsField, inBuilder.allowEmailNotifsField);
	}

	/**
	 * Creates a new notification traveller
	 * 
	 * @param inId
	 *            the id of the traveller
	 * @param inUsername
	 *            the user name of the traveller
	 * @param inPhone
	 *            the phone number of the travller
	 * @param inEmail
	 *            the email of the travller
	 * @param inAllowSms
	 *            true if the traveller allows sms notifications
	 * @param inAllowEmail
	 *            true if the traveller allows email notifications
	 */
	@JsonCreator
	private NotificationTraveller(
			@JsonProperty(value = "id") final Long inId,
			@JsonProperty(value = "username") final String inUsername,
			@JsonProperty(value = "phoneNumber") final String inPhone,
			@JsonProperty(value = "email") final String inEmail,
			@JsonProperty(value = "smsNotificationAllowed") final boolean inAllowSms,
			@JsonProperty(value = "emailNotificationAllowed") final boolean inAllowEmail)
	{
		id = inId;
		username = inUsername;
		phoneNumber = inPhone;
		email = inEmail;
		smsNotificationAllowed = inAllowSms;
		emailNotificationAllowed = inAllowEmail;
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	@Override
	public boolean isSmsNotificationAllowed()
	{
		return smsNotificationAllowed;
	}

	@Override
	public boolean isEmailNotificationAllowed()
	{
		return emailNotificationAllowed;
	}
}
