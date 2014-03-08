package org.shared.traveller.client.domain.rest.event;

import java.util.Date;

import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.ITraveller;
import org.shared.traveller.client.domain.event.INewRequestEvent;
import org.shared.traveller.utility.DeepCopier;

/**
 * The class represents an event used when a new request instance is to be
 * created
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class NewRequestEvent implements INewRequestEvent
{
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -3391106766190335044L;

    private static final String NULL_SENDER =
            "The sender may not be null.";

    private static final String NULL_ANNOUNCEMENT =
            "The announcement may not be null.";

    private String sender;

    private String fromPoint;

    private String toPoint;

    private Date departureDate;

    private String driver;

    /**
     * This constructor is merely intended to be used for deserialization
     * purposes. It should not be used by any user code.
     */
    protected NewRequestEvent()
    {
        // used for JSON deserialization purposes
    }

    /**
     * The constructor creates a new event
     * 
     * @param inSender
     *            the user name of the request's sender. It may not be null.
     * @param inAnnouncement
     *            the announcement for which the request is sent. It may not be
     *            null.
     */
    public NewRequestEvent(final ITraveller inSender,
            final IAnnouncement inAnnouncement)
    {
        assert null != inSender : NULL_SENDER;
        assert null != inAnnouncement : NULL_ANNOUNCEMENT;

        sender = inSender.getUsername();
        fromPoint = inAnnouncement.getFrom();
        toPoint = inAnnouncement.getTo();
        departureDate = DeepCopier.copy(inAnnouncement.getDepartureDate());
        driver = inAnnouncement.getDriverUsername();
    }

    @Override
    public String getSender()
    {
        return sender;
    }

    @Override
    public String getFromPoint()
    {
        return fromPoint;
    }

    @Override
    public String getToPoint()
    {
        return toPoint;
    }

    @Override
    public Date getDepartureDate()
    {
        return DeepCopier.copy(departureDate);
    }

    @Override
    public String getDriver()
    {
        return driver;
    }
}
