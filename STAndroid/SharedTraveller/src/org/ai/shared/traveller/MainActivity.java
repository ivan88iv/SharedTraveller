package org.ai.shared.traveller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ai.shared.traveller.announcement.activity.ShowAnnouncementsActivity;
import org.ai.shared.traveller.announcement.input.InputAnnouncementActivity;
import org.ai.shared.traveller.call.CallEnder;
import org.ai.shared.traveller.client.factory.builder.IBuilderFactory;
import org.ai.shared.traveller.command.request.INewRequestCommand;
import org.ai.shared.traveller.command.save.announcement.ISaveAnnouncementCommand;
import org.ai.shared.traveller.data.providers.ICitiesProvider;
import org.ai.shared.traveller.data.providers.IVehiclesProvider;
import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialogFragment;
import org.ai.shared.traveller.dialog.request.NewRequestDialogContext;
import org.ai.shared.traveller.dialog.request.RequestCreationNotificationContext;
import org.ai.shared.traveller.dialog.request.RequestStatusNotificationContext;
import org.ai.shared.traveller.dialog.trip.TripCancellationNotificationContext;
import org.ai.shared.traveller.factory.client.IServiceClientFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.client.IServiceClient;
import org.ai.shared.traveller.network.connection.task.AllCitiesTask;
import org.ai.shared.traveller.network.connection.task.UserVehiclesTask;
import org.ai.shared.traveller.network.connection.task.announcement.NewAnnouncementTask;
import org.ai.shared.traveller.network.connection.task.request.DeclineRequestTask;
import org.ai.shared.traveller.network.connection.task.request.NewRequestTask;
import org.ai.shared.traveller.network.connection.task.trip.CancelTripTask;
import org.ai.shared.traveller.notification.NotificationServiceConfigurator;
import org.ai.shared.traveller.notification.social.CancelledTripNotification;
import org.ai.shared.traveller.notification.social.DeclinedRequestNotification;
import org.ai.shared.traveller.notification.social.NewRequestNotification;
import org.ai.shared.traveller.notification.social.SocialNotificationSender;
import org.ai.shared.traveller.request.AnnouncementRequestActivity;
import org.ai.shared.traveller.request.UserRequestsActivity;
import org.ai.shared.traveller.settings.SettingsActivity;
import org.ai.shared.traveller.ui.blocker.UIBlocker;
import org.ai.shared.traveller.ui.preparator.ICityComponentsPreparator;
import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.IRequestInfo.IBuilder;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.traveller.INotificationTraveller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

public class MainActivity extends AbstractNetworkActivity implements
		ISaveAnnouncementCommand, INewRequestCommand,
		ICitiesProvider, IVehiclesProvider, ISimpleDialogListener
{
	private static final String UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT =
			"Could not submit the announcement {0}.";

	private static final String CREATION_ANNOUNCEMNT_TASK_KEY =
			"newAnnouncementTask";

	private static final String NEW_REQUEST_TASK_KEY = "sendRequestTask";

	private static final String REQUEST_DECLINATION_TASK_KEY =
			"declineRequestTask";

	private static final String TRIP_CANCELLATION_TASK_KEY =
			"tripCancellation";

	private static final String SUCCESSFUL_REQUEST_DECLINATION =
			"The request was successfully declined.";

	private static final String REQUEST_DECLINATION_PROBLEM =
			"A problem occurred while trying to decline the request.";

	private static final String SUCCESSFUL_TRIP_CANCELLATION =
			"The trip was cancelled successfully.";

	private static final String TRIP_CANCELLATION_PROBLEM =
			"A problem occurred while trying to cancel the trip.";

	private final IServiceClientFactory clientFactory =
			DomainManager.getInstance().getServiceClientFactory();

	private RequestStatusNotificationContext declineNotificationContext;

	private TripCancellationNotificationContext cancelTripNotificationContext;

	private IRequestInfo declinationRequestInfo;

	private IRequestedAnnouncement tripToCancel;

	private boolean sendNotification;

	private UIBlocker uiBlocker;

	private IAnnouncement requestedAnnouncement;

	private RequestCreationNotificationContext newRequestDialogContext;

	private INotificationTraveller requestedDriver;

	private IRequestInfo newRequest;

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		if (R.id.action_settings == item.getItemId())
		{
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void attachTasks()
	{
		// No tasks are attached on activity startup
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiBlocker = new UIBlocker(findViewById(R.id.trips_progress_indicator));

		attachCallListener();

		final Button showViewPagerIndicator = (Button) findViewById(
				R.id.show_view_pager_indicator);
		final Button showSwipeView = (Button) findViewById(
				R.id.show_swipe_list_view);
		final Button showNewRequestDialog = (Button) findViewById(
				R.id.show_new_request_dialog);
		final Button showRequestsBtn = (Button) findViewById(
				R.id.show_requests);
		final Button showMyRequestsBtn = (Button) findViewById(
				R.id.show_my_requests);
		final Button requestDeclinationBtn = (Button) findViewById(
				R.id.send_request_declination_notification);
		final Button travelCancelationBtn = (Button) findViewById(
				R.id.travel_cancelation_notification);

		showViewPagerIndicator.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final Intent intent = new Intent(MainActivity.this,
						InputAnnouncementActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		showSwipeView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final Intent intent = new Intent(MainActivity.this,
						ShowAnnouncementsActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		// TODO replace hardcoded announcement
		final IBuilderFactory builderFactory = DomainManager.getInstance()
				.getBuilderFactory();
		final IAnnouncement.IBuilder annonBuilder = builderFactory
				.createAnnouncementBuilder("Bansko", "Sofia",
						new Date(114, 1, 9), (short) 5, "temp");
		requestedAnnouncement = annonBuilder.build();
		final INotificationTraveller.IBuilder travellerBuilder =
				builderFactory.createNotificationTravellerBuilder();
		requestedDriver = travellerBuilder.id(1l)
				.username(
						requestedAnnouncement.getDriverUsername())
				.phoneNumber("0888888888")
				.email("temp@temp.com")
				.allowEmailNotifications(true)
				.allowSmsNotifications(true).build();

		showNewRequestDialog.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				STDialogFragment.show(new NewRequestDialogContext(
						MainActivity.this, requestedAnnouncement));
			}
		});

		showRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				final Intent requestsIntent = new Intent(MainActivity.this,
						AnnouncementRequestActivity.class);
				// TODO replace the hard-coded announcement id
				requestsIntent.putExtra(
						AnnouncementRequestActivity.ANNOUNCEMENT_ID_KEY, 1l);

				startActivity(requestsIntent);
			}
		});

		showMyRequestsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(MainActivity.this,
						UserRequestsActivity.class));
			}
		});

		// TODO replace hard-coded values
		final INotificationTraveller driver =
				travellerBuilder.phoneNumber("0888888888")
						.email("temp@temp.com")
						.allowEmailNotifications(true)
						.allowSmsNotifications(true)
						.build();
		final IBuilder declineRequestBuilder = builderFactory
				.createRequestInfoBuilder();
		declinationRequestInfo = declineRequestBuilder.id(31l)
				.sender("temp").driver(driver).build();

		requestDeclinationBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				declineNotificationContext = new RequestStatusNotificationContext(
						MainActivity.this, "request_declination_notification",
						declinationRequestInfo.getDriver()
								.isSmsNotificationAllowed(),
						declinationRequestInfo.getDriver()
								.isEmailNotificationAllowed());
				STDialogFragment.show(declineNotificationContext);
			}
		});

		// TODO replace the hard-coded data
		final INotificationTraveller.IBuilder senderBuilder =
				builderFactory.createNotificationTravellerBuilder();
		final INotificationTraveller requestSender =
				senderBuilder.allowEmailNotifications(true)
						.allowSmsNotifications(true)
						.username("temp").phoneNumber("0888888888")
						.email("temp@temp.com").build();
		final List<IPlainRequest> requests = new ArrayList<IPlainRequest>();
		requests.add(DomainManager.getInstance().getDomainFactory()
				.createRequest(31l, RequestStatus.PENDING, requestSender));
		final org.shared.traveller.client.domain.IRequestedAnnouncement.IBuilder annBuilder = DomainManager
				.getInstance().getBuilderFactory()
				.createRequestedAnnouncementBuilder();
		tripToCancel = annBuilder.id(1l).departureDate(new Date(114, 1, 9))
				.seats((short) 5).status(Status.ACTIVE)
				.requests(requests).build();

		travelCancelationBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				cancelTripNotificationContext = new TripCancellationNotificationContext(
						MainActivity.this, "travel_cancelation_notification");
				STDialogFragment.show(cancelTripNotificationContext);
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		final NotificationServiceConfigurator serviceConfig =
				new NotificationServiceConfigurator(getApplicationContext());
		serviceConfig.configure();
	}

	@Override
	public void onBackPressed()
	{

	}

	@Override
	protected void onSaveInstanceState(final Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	@Override
	public void saveAnnouncement(final Announcement inAnnouncement)
	{
		final IServiceClient newAnnouncementClient =
				clientFactory.createNewResourceClient(MainActivity.this,
						"announcement/new", inAnnouncement,
						MessageFormat.format(UNSUCCESSFUL_ANNOUNCEMENT_SUBMIT,
								inAnnouncement));
		addTask(CREATION_ANNOUNCEMNT_TASK_KEY, new NewAnnouncementTask(this,
				newAnnouncementClient));
		executeTask(CREATION_ANNOUNCEMNT_TASK_KEY);
	}

	@Override
	public void sendRequest(final IRequestInfo inRequest)
	{
		addTask(NEW_REQUEST_TASK_KEY, new NewRequestTask(this, inRequest));
		executeTask(NEW_REQUEST_TASK_KEY);
	}

	@Override
	public void provideCityNames(final ICityComponentsPreparator inPreparator)
	{
		final IServiceClient getClient = clientFactory.createSimpleClient(this,
				"cities/all");
		final AllCitiesTask citiesTask = new AllCitiesTask(this, getClient,
				inPreparator);
		addTask("CITIES_TASK", citiesTask);
		executeTask("CITIES_TASK");
	}

	@Override
	public void provideVehicleNames(final String inUsername,
			final IVehicleComponentsPreparator inPreparator)
	{
		final IServiceClient getClient = clientFactory.createSimpleClient(this,
				"vehicles/" + inUsername);
		final UserVehiclesTask vehicleTask = new UserVehiclesTask(this,
				getClient, inPreparator);
		addTask("VEHICLE_TASK", vehicleTask);
		executeTask("VEHICLE_TASK");
	}

	@Override
	public void onPositiveButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.NEW_REQUEST
				.getCode())
		{
			final IBuilderFactory factory =
					DomainManager.getInstance().getBuilderFactory();
			final IRequestInfo.IBuilder builder = factory
					.createRequestInfoBuilder();
			// TODO replace the hard-coded sender
			builder.sender("temp").fromPoint(requestedAnnouncement.getFrom())
					.toPoint(requestedAnnouncement.getTo())
					.departureDate(requestedAnnouncement.getDepartureDate())
					.driver(requestedDriver);
			newRequest = builder.build();
			newRequestDialogContext = new RequestCreationNotificationContext(
					MainActivity.this,
					requestedDriver.isSmsNotificationAllowed(),
					requestedDriver.isEmailNotificationAllowed());
			STDialogFragment.show(newRequestDialogContext);
		} else if (requestCode == DialogRequestCode.REQUEST_CREATION_NOTIFICATION
				.getCode())
		{
			sendRequest(newRequest);
			sendNotification = true;
		} else if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			addTask(REQUEST_DECLINATION_TASK_KEY, new DeclineRequestTask(
					this, declinationRequestInfo.getId()));
			executeTask(REQUEST_DECLINATION_TASK_KEY);
			sendNotification = true;
			uiBlocker.block(true);
		} else if (requestCode == DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION
				.getCode())
		{
			addTask(TRIP_CANCELLATION_TASK_KEY, new CancelTripTask(
					this, tripToCancel.getId()));
			executeTask(TRIP_CANCELLATION_TASK_KEY);
			sendNotification = true;
			uiBlocker.block(true);
		}
	}

	@Override
	public void onNegativeButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.REQUEST_CREATION_NOTIFICATION
				.getCode())
		{
			sendRequest(newRequest);
			sendNotification = false;
		} else if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			addTask(REQUEST_DECLINATION_TASK_KEY, new DeclineRequestTask(
					MainActivity.this, declinationRequestInfo.getId()));
			executeTask(REQUEST_DECLINATION_TASK_KEY);
			sendNotification = false;
			uiBlocker.block(true);
		} else if (requestCode == DialogRequestCode.CANCEL_TRAVEL_NOTIFICATION
				.getCode())
		{
			// TODO replace the hard-coded announcement id
			addTask(TRIP_CANCELLATION_TASK_KEY, new CancelTripTask(
					this, Long.valueOf(1)));
			executeTask(TRIP_CANCELLATION_TASK_KEY);
			sendNotification = false;
			uiBlocker.block(true);
		}
	}

	/**
	 * The method is called when a request has been declined successfully
	 */
	public void onSuccessfulRequestDeclination()
	{
		if (sendNotification)
		{
			String phoneNumber = null;
			Long recipientId = null;
			if (declineNotificationContext.isSmsNotificationOn())
			{
				phoneNumber = declinationRequestInfo.getDriver()
						.getPhoneNumber();
			}

			if (declineNotificationContext.isEmailNotificationOn())
			{
				recipientId = declinationRequestInfo.getDriver().getId();
			}

			final SocialNotificationSender notificationSender =
					new SocialNotificationSender(this,
							new DeclinedRequestNotification(
									this, declinationRequestInfo));
			notificationSender.send(phoneNumber, recipientId);
		}

		uiBlocker.block(false);
		Toast.makeText(this, SUCCESSFUL_REQUEST_DECLINATION,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when a problem occurs in the process of a request
	 * declination
	 * 
	 */
	public void onRequestDeclinationProblem()
	{
		uiBlocker.block(false);
		Toast.makeText(this, REQUEST_DECLINATION_PROBLEM, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * The method is called when a trip is successfully cancelled
	 */
	public void onSuccessfulTripCancellation()
	{
		if (sendNotification)
		{
			final boolean isSmsNotificationOn =
					cancelTripNotificationContext.isSmsNotificationOn();
			final boolean isEmailNotificationOn =
					cancelTripNotificationContext.isEmailNotificationOn();

			for (final IPlainRequest tripRequest : tripToCancel.getRequests())
			{
				String phoneNumber = null;
				Long recipientId = null;
				if (isSmsNotificationOn)
				{
					phoneNumber = tripRequest.getSender().getPhoneNumber();
				}

				if (isEmailNotificationOn)
				{
					recipientId = tripRequest.getSender().getId();
				}

				final SocialNotificationSender notificationSender =
						new SocialNotificationSender(this,
								new CancelledTripNotification(this,
										tripToCancel));
				notificationSender.send(phoneNumber, recipientId);
			}
		}

		uiBlocker.block(false);
		Toast.makeText(this, SUCCESSFUL_TRIP_CANCELLATION,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when there was a problem while trying to cancel a
	 * trip
	 */
	public void onTripCancellationProblem()
	{
		uiBlocker.block(false);
		Toast.makeText(this, TRIP_CANCELLATION_PROBLEM, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * The method is called when a new request has been successfully posted for
	 * the selected announcement
	 */
	public void onSuccessfulNewRequestSending()
	{
		if (sendNotification)
		{
			final boolean isSmsNotificationOn =
					newRequestDialogContext.isSmsNotificationOn();
			final boolean isEmailNotificationOn =
					newRequestDialogContext.isEmailNotificationOn();

			String phoneNumber = null;
			Long recipientId = null;
			if (isSmsNotificationOn)
			{
				phoneNumber = newRequest.getDriver().getPhoneNumber();
			}

			if (isEmailNotificationOn)
			{
				recipientId = newRequest.getDriver().getId();
			}

			final SocialNotificationSender notificationSender =
					new SocialNotificationSender(this,
							new NewRequestNotification(this, newRequest));
			notificationSender.send(phoneNumber, recipientId);
		}
		Toast.makeText(this, getResources().getString(
				R.string.new_request_send_success),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * The method is called when there has been a problem while trying to post a
	 * new request for the selected announcement
	 * 
	 */
	public void onNewRequestSendingProblem()
	{
		Toast.makeText(this, getResources().getString(
				R.string.new_request_send_problem),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * The method attaches listeners to call made from the device
	 */
	private void attachCallListener()
	{
		final CallEnder callEnder = new CallEnder(this);
		final TelephonyManager callManager = (TelephonyManager)
				getSystemService(Context.TELEPHONY_SERVICE);
		callManager.listen(callEnder, PhoneStateListener.LISTEN_CALL_STATE);
	}
}
