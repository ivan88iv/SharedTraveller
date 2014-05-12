package org.ai.shared.traveller.network.connection.task.notification;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.ai.shared.traveller.notification.social.MailingService;
import org.shared.traveller.rest.domain.ErrorResponse;
import org.shared.traveller.utility.InstanceAsserter;

import android.content.Context;
import android.util.Log;

/**
 * The task is responsible for calling a service that sends emails to the
 * specified recipient from the server side
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class SendEmailNotificationTask extends
		AbstractNetworkTask<MailingService, Void>
{
	/**
	 * Creates a new task
	 * 
	 * @param inService
	 *            the mailing service which calls the task. It may not be null
	 * @param inMessage
	 *            the message which should be sent. It may not be null
	 * @param inReceiverId
	 *            the id of the message receiver. It may not be null
	 */
	public SendEmailNotificationTask(final MailingService inService,
			final String inMessage, final Long inReceiverId)
	{
		super(inService, constructClient(inService, inMessage, inReceiverId),
				Void.class);
	}

	@Override
	protected void onSuccess(Void inResult)
	{
		Log.d("SendEmailNotificationTask",
				"Successful email notification sending");
		getContext().onSuccessfulMailSend();
	}

	@Override
	protected void onError(int inStatusCode, ErrorResponse inError)
	{
		Log.d("SendEmailNotificationTask", MessageFormat.format(
				"The email notification could not be sent: status code {0},"
						+ " response {1}", inStatusCode, inError));
		getContext().onErrorMailSend();
	}

	/**
	 * The method constructs the client for performing this service call
	 * 
	 * @param inContext
	 *            the context in which the task is called. It may not be null
	 * @param inMessage
	 *            the message to be sent by the task. It may not be null
	 * @param inReceiverId
	 *            the id of the message receiver. It may not be null
	 * @return the created service client
	 */
	private static IServiceClient constructClient(final Context inContext,
			final String inMessage, final Long inReceiverId)
	{
		InstanceAsserter.assertNotNull(inMessage, "message");
		InstanceAsserter.assertNotNull(inReceiverId, "receiver's id");

		final Map<String, String> params = new HashMap<String, String>();
		params.put("message", inMessage);
		params.put("receiverId", String.valueOf(inReceiverId));
		final IServiceClientFactory factory = DomainManager.getInstance()
				.getServiceClientFactory();
		return factory.createFormSubmitionClient(inContext,
				"notification/send/email", params);
	}
}
