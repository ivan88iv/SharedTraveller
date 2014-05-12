package org.ai.shared.traveller.request;

import org.ai.shared.traveller.call.CallerActivity;
import org.ai.shared.traveller.command.request.IRequestExtractionCommand;
import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialogFragment;
import org.ai.shared.traveller.dialog.request.RequestStatusNotificationContext;
import org.ai.shared.traveller.network.connection.task.request.AcceptRequestTask;
import org.ai.shared.traveller.network.connection.task.request.RejectRequestTask;
import org.ai.shared.traveller.network.connection.task.request.RequestsExtractionTask;
import org.ai.shared.traveller.notification.NotificationRemover;
import org.ai.shared.traveller.notification.NotifiedIntentManager;
import org.ai.shared.traveller.notification.social.AcceptedRequestNotification;
import org.ai.shared.traveller.notification.social.ISocialNotification;
import org.ai.shared.traveller.notification.social.RejectedRequestNotification;
import org.ai.shared.traveller.notification.social.SocialNotificationSender;
import org.ai.shared.traveller.ui.blocker.UIBlocker;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IRequestedAnnouncement;
import org.shared.traveller.client.domain.request.IPlainRequest;
import org.shared.traveller.utility.InstanceAsserter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

/**
 * The class is responsible for creating the calculations logic behind the
 * travel requests screen
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class AnnouncementRequestActivity extends CallerActivity
		implements IRequestExtractionCommand, ISimpleDialogListener,
		IRequestSelectionListener
{
	/**
	 * The key that holds the id of the announcement for which requests are
	 * shown by the current activity
	 */
	public static final String ANNOUNCEMENT_ID_KEY = "announcementId";

	private static final String ACCEPT_REQUEST_TASK =
			"accept_request";

	private static final String REJECT_REQUEST_TASK =
			"reject_request";

	private int previousReqCode = DialogRequestCode.NONE.getCode();

	private int lastSelectedRequestInd;

	private RequestStatusNotificationContext lastNotificationDialogContext;

	private boolean isViewConfiguredOnStartup = false;

	private boolean sendSocialNotifications;

	private UIBlocker blocker;

	@Override
	protected void onCreate(final Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.activity_announcement_request);
		blocker = new UIBlocker(findViewById(R.id.requests_progress_indicator));
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		configureView();
		isViewConfiguredOnStartup = true;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		final NotificationRemover remover = new NotificationRemover(this);
		remover.removeNotifications();
		final NotifiedIntentManager manager = NotifiedIntentManager
				.getInstance();
		if (manager.isMarked(getIntent()) && !isViewConfiguredOnStartup)
		{
			configureView();
		} else
		{
			isViewConfiguredOnStartup = false;
		}
	}

	@Override
	protected void attachTasks()
	{
		// No tasks are attached to this activity
	}

	@Override
	public void extractRequests(final Long inAnnouncementId,
			final RequestsAdapter inAdapter)
	{
		final RequestsExtractionTask task = new RequestsExtractionTask(
				this, inAnnouncementId, inAdapter);
		addTask("REQUESTS_EXTRACTION", task);
		executeTask("REQUESTS_EXTRACTION");
	}

	/**
	 * The method is called on successful extraction of new requests. It applies
	 * the newly extracted requests to the specified adapter.
	 * 
	 * @param inNewRequests
	 *            the extracted request information
	 * @param inAdapter
	 *            the adapter responsible for displaying the provided requests.
	 *            It may not be null.
	 */
	public void onRequestsExtractionSuccess(
			final IRequestedAnnouncement inNewRequests,
			final RequestsAdapter inAdapter)
	{
		InstanceAsserter.assertNotNull(inAdapter, "adapter");

		inAdapter.setRequestInfo(inNewRequests);
		Toast.makeText(this, "Successful requests extraction!",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * The method is called when the requests extraction from the server side is
	 * not successful
	 */
	public void onRequestsExtractionError()
	{
		Toast.makeText(this,
				"No requests were extracted because of a problem.",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPositiveButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.ACCEPT_REQUEST.getCode())
		{
			final IPlainRequest request = getLastSelectedRequest();
			lastNotificationDialogContext = new RequestStatusNotificationContext(
					this, "notification_accept_request",
					request.getSender().isSmsNotificationAllowed(),
					request.getSender().isEmailNotificationAllowed());
			STDialogFragment.show(lastNotificationDialogContext);
		} else if (requestCode == DialogRequestCode.REJECT_REQUEST
				.getCode())
		{
			final IPlainRequest request = getLastSelectedRequest();
			lastNotificationDialogContext = new RequestStatusNotificationContext(
					this, "notification_reject_request",
					request.getSender().isSmsNotificationAllowed(),
					request.getSender().isEmailNotificationAllowed());
			STDialogFragment.show(lastNotificationDialogContext);
		} else if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			changeRequestStatus(previousReqCode);
			sendSocialNotifications = true;
		}

		previousReqCode = requestCode;
	}

	@Override
	public void onNegativeButtonClicked(final int requestCode)
	{
		if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			changeRequestStatus(previousReqCode);
			sendSocialNotifications = false;
		}

		previousReqCode = DialogRequestCode.NONE.getCode();
	}

	@Override
	public void onRequestSelect(final int inRequestInd)
	{
		lastSelectedRequestInd = inRequestInd;
	}

	/**
	 * The method is called when the last selected request has been accepted
	 * successfully
	 * 
	 * @param inRequestInfo
	 *            the new request information that takes into account the recent
	 *            request acceptance
	 */
	public void onRequestAccept(final IRequestedAnnouncement inRequestInfo)
	{
		final RequestsAdapter adapter = loadAdapter();
		sendSocialNotifications(new AcceptedRequestNotification(this,
				adapter.getRequestInfo()));
		adapter.setRequestInfo(inRequestInfo);

		blocker.block(false);
		Toast.makeText(this, "The request has been accepted successfully!",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when the lastly selected request acceptance fails
	 * 
	 */
	public void onRequestAcceptError()
	{
		blocker.block(false);
		Toast.makeText(this,
				"The request could not be accepted because of a problem.",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when the last selected request has been successfully
	 * rejected
	 * 
	 * @param inNewRequestInfo
	 *            the new request information that represents the successful
	 *            rejection
	 */
	public void onRequestRejection(
			final IRequestedAnnouncement inNewRequestInfo)
	{
		final RequestsAdapter adapter = loadAdapter();
		sendSocialNotifications(new RejectedRequestNotification(this,
				adapter.getRequestInfo()));
		adapter.setRequestInfo(inNewRequestInfo);

		blocker.block(false);
		Toast.makeText(this, "The request has been rejected successfully!",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * The method is called when the last selected request has not been rejected
	 * successfully due to a problem
	 */
	public void onRequestRejectionError()
	{
		blocker.block(false);
		Toast.makeText(this,
				"The request could not be rejected because of a problem.",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * The method changes the request status of the lastly selected request
	 * depending on the request code
	 * 
	 * @param inRequestCode
	 *            the code of request being performed. It determines whether the
	 *            status shall be changed to accepted or to rejected
	 */
	private void changeRequestStatus(final int inRequestCode)
	{
		if (inRequestCode == DialogRequestCode.ACCEPT_REQUEST.getCode())
		{
			addTask(ACCEPT_REQUEST_TASK, new AcceptRequestTask(this,
					lastSelectedRequestInd, loadAdapter().getRequestInfo()));
			executeTask(ACCEPT_REQUEST_TASK);
			blocker.block(true);
		} else if (inRequestCode == DialogRequestCode.REJECT_REQUEST
				.getCode())
		{
			addTask(REJECT_REQUEST_TASK, new RejectRequestTask(this,
					lastSelectedRequestInd, loadAdapter().getRequestInfo()));
			executeTask(REJECT_REQUEST_TASK);
			blocker.block(true);
		}
	}

	/**
	 * The method configures the view displayed in the current activity
	 */
	private void configureView()
	{
		final FragmentManager manager = getSupportFragmentManager();
		final FragmentTransaction transaction =
				manager.beginTransaction();
		final Long announcementId = Long.valueOf(getIntent().getExtras()
				.getLong(ANNOUNCEMENT_ID_KEY));

		transaction.replace(R.id.requests_fragment_container,
				AnnouncementRequestsFragment.newInstance(this,
						announcementId));
		transaction.commit();
	}

	/**
	 * The method loads the adapter for the travel requests
	 * 
	 * @param inActivity
	 *            the activity for which the adapter is loaded
	 * @return the loaded request adapter
	 */
	private RequestsAdapter loadAdapter()
	{
		final AnnouncementRequestsFragment fragment =
				(AnnouncementRequestsFragment)
				getSupportFragmentManager().findFragmentById(
						R.id.requests_fragment_container);

		return fragment.getAdapter();
	}

	/**
	 * Returns the lastly selected request
	 * 
	 * @return the lastly selected request
	 */
	private IPlainRequest getLastSelectedRequest()
	{
		final RequestsAdapter adapter = loadAdapter();
		return adapter.getItem(lastSelectedRequestInd);
	}

	/**
	 * The method sends the specified social notification either via sms or via
	 * email depending on the user selection in the last notification dialog
	 * 
	 * @param inNotification
	 *            the notification to be sent. It may not be null
	 */
	private void sendSocialNotifications(
			final ISocialNotification inNotification)
	{
		if (sendSocialNotifications)
		{
			final IPlainRequest lastSelectedRequest =
					getLastSelectedRequest();

			String phoneNumber = null;
			Long recipientId = null;

			if (lastNotificationDialogContext.isSmsNotificationOn())
			{
				phoneNumber = lastSelectedRequest.getSender()
						.getPhoneNumber();
			}

			if (lastNotificationDialogContext.isEmailNotificationOn())
			{
				recipientId = lastSelectedRequest.getSender().getId();
			}

			final SocialNotificationSender sender =
					new SocialNotificationSender(this, inNotification);
			sender.send(phoneNumber, recipientId);
		}
	}
}
