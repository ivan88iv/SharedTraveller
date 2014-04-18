package org.ai.shared.traveller.request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ai.shared.traveller.command.request.IRequestExtractionCommand;
import org.ai.shared.traveller.dialog.DialogRequestCode;
import org.ai.shared.traveller.dialog.STDialog;
import org.ai.shared.traveller.dialog.request.NotificationDialogFactory;
import org.ai.shared.traveller.network.connection.AbstractNetworkActivity;
import org.ai.shared.traveller.network.connection.rest.client.AbstractPostClient;
import org.ai.shared.traveller.network.connection.rest.client.RequestTypes;
import org.ai.shared.traveller.network.connection.rest.client.SimpleClient;
import org.ai.shared.traveller.task.request.AcceptRequestTask;
import org.ai.shared.traveller.task.request.RequestsExtractionTask;
import org.ai.sharedtraveller.R;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;

public class AnnouncementRequestActivity extends AbstractNetworkActivity
        implements IRequestExtractionCommand, ISimpleDialogListener,
        IRequestSelectionListener
{
    private static final String ACCEPT_REQUEST_TASK =
            "accept_request";

    private int previousReqCode = DialogRequestCode.NONE.getCode();

    private Long lastSelectedRequestId;

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
        final SimpleClient client = new SimpleClient(RequestTypes.GET);
        final RequestsExtractionTask task = new RequestsExtractionTask(
                this, inAnnouncement, inAdapter, client);
        addTask("REQUESTS_EXTRACTION", task);
        executeTask("REQUESTS_EXTRACTION");
    }

    @Override
    public void onPositiveButtonClicked(final int requestCode)
    {
        if (requestCode == DialogRequestCode.ACCEPT_REQUEST_CODE.getCode())
        {
            STDialog.show(new NotificationDialogFactory(this,
                    "notification_accept_request"));
        } else if (requestCode == DialogRequestCode.REJECT_REQUEST_CODE
                .getCode())
        {
            STDialog.show(new NotificationDialogFactory(this,
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
    public void onRequestSelect(final Long inRequestId)
    {
        lastSelectedRequestId = inRequestId;
    }

    private void changeRequestStatus(final int inRequestCode)
    {
        if (inRequestCode == DialogRequestCode.ACCEPT_REQUEST_CODE.getCode())
        {
            final AbstractPostClient client =
                    new AbstractPostClient()
                    {
                        @Override
                        protected Map<String, String>
                                prepareRequestParameters()
                        {
                            final Map<String, String> parameters =
                                    new HashMap<String, String>();
                            parameters.put("id",
                                    String.valueOf(lastSelectedRequestId));
                            return parameters;
                        }
                    };

            addTask(ACCEPT_REQUEST_TASK, new AcceptRequestTask(this, client));
            executeTask(ACCEPT_REQUEST_TASK);
        } else if (inRequestCode == DialogRequestCode.REJECT_REQUEST_CODE
                .getCode())
        {

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
        final AnnouncementBuilder builder = new AnnouncementBuilder(
                "Bansko", "Sofia", new Date(114, 1, 9),
                (short) 5, "temp");
        transaction.add(R.id.requests_fragment_container,
                AnnouncementRequestsFragment.newInstance(this,
                        builder.build()));
        transaction.commit();
    }
}
