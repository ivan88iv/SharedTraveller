package org.ai.shared.traveller.task.request;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.ai.shared.traveller.MainActivity;
import org.ai.shared.traveller.exceptions.ServiceConnectionException;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPutClient;
import org.ai.shared.traveller.network.connection.task.AbstractNetworkTask;
import org.codehaus.jackson.map.ObjectMapper;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.rest.domain.ErrorResponse;

import android.util.Log;
import android.widget.Toast;

/**
 * The class represents a task used to send new business requests to the server
 * via REST services.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestTask extends AbstractNetworkTask<MainActivity, Void>
{
	private static final String NEW_REQUEST_FAILED =
			"Could not send new request for {0}.";

	/**
	 * Instantiates a new task for creating announcement instances at the server
	 * 
	 * @param inActivity
	 *            the activity this task is connected to
	 * @param inRequestInfo
	 *            the instance holds the request information used to create the
	 *            new travel request in the server side
	 */
	public NewRequestTask(final MainActivity inActivity,
			final IRequestInfo inRequestInfo)
	{
		super(inActivity, "request/new", prepareRestClient(inRequestInfo),
				Void.class);
	}

	@Override
	protected void onSuccess(final Void inResult)
	{
		Log.d("NewRequestTask", "A new request has been sent successfully!");
		Toast.makeText(getActivity(),
				"A new request has been sent successfully!",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onError(final int inStatusCode, final ErrorResponse inError)
	{
		Log.d("NewRequestTask",
				"The request could not be performed successfully.");
		Toast.makeText(getActivity(),
				"A request could not be sent because of a problem.",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method creates the REST client used to create a new travel request
	 * from the information provided
	 * 
	 * @param inRequest
	 *            the request information used for the new travel request
	 * @return the new REST client used to make the connection to the server
	 */
	private static AbstractPutClient prepareRestClient(
			final IRequestInfo inRequest)
	{
		return new AbstractPutClient()
		{
			@Override
			protected void submitData(final OutputStream inStream)
					throws ServiceConnectionException
			{
				final ObjectMapper writer = new ObjectMapper();

				try
				{
					writer.writeValue(inStream, inRequest);
				} catch (final IOException ioe)
				{
					final String errorMsg = MessageFormat.format(
							NEW_REQUEST_FAILED, inRequest);

					throw new ServiceConnectionException(errorMsg, ioe);
				}
			}
		};
	}
}
