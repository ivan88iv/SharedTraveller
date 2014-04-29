package org.ai.shared.traveller.request;

import java.util.Date;
import java.util.List;

import org.ai.shared.traveller.call.CallerActivity;
import org.ai.shared.traveller.command.request.IRequestExtractionCommand;
import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialogFragment;
import org.ai.shared.traveller.dialog.request.RequestStatusNotificationFactory;
import org.ai.shared.traveller.factory.builder.IBuilderFactory;
import org.ai.shared.traveller.manager.domain.DomainManager;
import org.ai.shared.traveller.task.request.AcceptRequestTask;
import org.ai.shared.traveller.task.request.RejectRequestTask;
import org.ai.shared.traveller.task.request.RequestsExtractionTask;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.InstanceAsserter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
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
	private static final String ACCEPT_REQUEST_TASK =
			"accept_request";

	private static final String REJECT_REQUEST_TASK =
			"reject_request";

	private int previousReqCode = DialogRequestCode.NONE.getCode();

	private int lastSelectedRequestInd;

	@Override
	protected void onCreate(final Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.activity_announcement_request);
		configureView();
	}

	@Override
	protected void attachTasks()
	{
		// No tasks are attached to this activity
	}

	@Override
	public void extractRequests(final IAnnouncement inAnnouncement,
			final RequestsAdapter inAdapter)
	{
		final RequestsExtractionTask task = new RequestsExtractionTask(
				this, inAnnouncement, inAdapter);
		addTask("REQUESTS_EXTRACTION", task);
		executeTask("REQUESTS_EXTRACTION");
	}

	/**
	 * The method is called on successful extraction of new requests. It applies
	 * the newly extracted requests to the specified adapter.
	 * 
	 * @param inNewRequests
	 *            the extracted requests
	 * @param inAdapter
	 *            the adapter responsible for displaying the provided requests.
	 *            It may not be null.
	 */
	public void onRequestsExtractionSuccess(
			final List<IRequestInfo> inNewRequests,
			final RequestsAdapter inAdapter)
	{
		InstanceAsserter.assertNotNull(inAdapter, "adapter");

		inAdapter.setRequests(inNewRequests);
		inAdapter.notifyDataSetChanged();
		Toast.makeText(this, "Successful requests extraction!",
				Toast.LENGTH_SHORT).show();
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
			STDialogFragment.show(new RequestStatusNotificationFactory(
					this,
					"notification_accept_request"));
		} else if (requestCode == DialogRequestCode.REJECT_REQUEST
				.getCode())
		{
			STDialogFragment.show(new RequestStatusNotificationFactory(
					this,
					"notification_reject_request"));
		} else if (requestCode == DialogRequestCode.REQUEST_NOTIFICATION
				.getCode())
		{
			changeRequestStatus(previousReqCode);
			// TODO implement notification sending here
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
	 */
	public void onRequestAccept()
	{
		final RequestsAdapter adapter = loadAdapter();
		final IRequestInfo acceptedRequest = adapter.getItem(
				lastSelectedRequestInd);
		acceptedRequest.setStatus(RequestStatus.APPROVED);
		final IAnnouncement prevAnnouncement =
				adapter.getSelectedAnnouncement();

		final short newSeats = (short) (prevAnnouncement.getSeats() - 1);
		final IBuilderFactory factory = DomainManager.getInstance()
				.getBuilderFactory();
		final IAnnouncement.IBuilder builder = factory
				.createAnnouncementBuilder(
						prevAnnouncement.getFrom(),
						prevAnnouncement.getTo(),
						prevAnnouncement.getDepartureDate(),
						newSeats, prevAnnouncement.getDriverUsername());
		final IAnnouncement newAnnouncement = builder
				.depAddress(prevAnnouncement.getDepAddress())
				.depTime(prevAnnouncement.getDepartureTime())
				.intermediatePoints(prevAnnouncement.getIntermediatePts())
				.price(prevAnnouncement.getPrice())
				.status(prevAnnouncement.getStatus()).build();
		adapter.setSelectedAnnouncement(newAnnouncement);
		adapter.notifyDataSetChanged();

		Toast.makeText(this, "The request has been accepted successfully!",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when the lastly selected request acceptance fails
	 * 
	 */
	public void onRequestAcceptError()
	{
		Toast.makeText(this,
				"The request could not be accepted because of a problem.",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when the last selected request has been successfully
	 * rejected
	 */
	public void onRequestRejection()
	{
		final ArrayAdapter<IRequestInfo> adapter = loadAdapter();
		final IRequestInfo acceptedRequest = adapter.getItem(
				lastSelectedRequestInd);
		acceptedRequest.setStatus(RequestStatus.REJECTED);
		adapter.notifyDataSetChanged();

		Toast.makeText(this, "The request has been rejected successfully!",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * The method is called when the last selected request has not been rejected
	 * successfully due to a problem
	 */
	public void onRequestRejectionError()
	{
		Toast.makeText(this,
				"The request could not be rejected because of a problem.",
				Toast.LENGTH_SHORT).show();
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
					getLastSelectedRequestId()));
			executeTask(ACCEPT_REQUEST_TASK);
		} else if (inRequestCode == DialogRequestCode.REJECT_REQUEST
				.getCode())
		{
			addTask(REJECT_REQUEST_TASK, new RejectRequestTask(this,
					getLastSelectedRequestId()));
			executeTask(REJECT_REQUEST_TASK);
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
		// TODO replace the hard-coded announcement with a one coming from
		// the bundle
		final IBuilderFactory factory =
				DomainManager.getInstance().getBuilderFactory();
		final IAnnouncement.IBuilder builder =
				factory.createAnnouncementBuilder("Bansko", "Sofia",
						new Date(114, 1, 9), (short) 5, "temp");
		builder.status(Status.ACTIVE);
		transaction.add(R.id.requests_fragment_container,
				AnnouncementRequestsFragment.newInstance(this,
						builder.build()));
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
	 * The method returns the id of the lastly selected request
	 * 
	 * @return the id of the lastly selected request or null if none is selected
	 */
	private Long getLastSelectedRequestId()
	{
		final ArrayAdapter<IRequestInfo> adapter = loadAdapter();
		Long requestId = null;
		final IRequestInfo selectedRequest =
				adapter.getItem(lastSelectedRequestInd);
		if (selectedRequest != null)
		{
			requestId = selectedRequest.getId();
		}
		return requestId;
	}
}
