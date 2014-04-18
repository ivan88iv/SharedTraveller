package org.shared.traveller.client.domain.rest;

import java.util.Date;

import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.client.domain.request.IRequestInfo;
import org.shared.traveller.client.domain.request.RequestStatus;
import org.shared.traveller.utility.DeepCopier;

/**
 * The class represents a request related information
 *
 * @author "Ivan Ivanov"
 *
 */
public class RequestInfo implements IRequestInfo
{
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -3391106766190335044L;

    private Long id;

    private String sender;

    private String fromPoint;

    private String toPoint;

    private Date departureDate;

    private String driver;

    private RequestStatus status;

    private Status announcementStatus;

    /**
     * The class is responsible for building a new request info instance
     *
     * @author "Ivan Ivanov"
     *
     */
    public static class RequestInfoBuilder
    {
    	private Long idField;

        private String senderField;

        private String fromField;

        private String toField;

        private Date departureDateField;

        private String driverField;

        private RequestStatus statusField;

        private Status announcementStatusField;

        /**
         * Sets a new value for the request's id
         *
         * @param inId the new value to be set
         * @return the current request info builder
         */
        public RequestInfoBuilder id(final Long inId)
        {
        	idField = inId;
        	return this;
        }

        /**
         * Sets a new user name to the sender field
         *
         * @param inSender
         *            the new sender user name
         * @return the builder
         */
        public RequestInfoBuilder sender(final String inSender)
        {
            senderField = inSender;
            return this;
        }

        /**
         * Sets a new settlement for a start point of the travel
         *
         * @param inFrom
         *            the new start point
         * @return the builder
         */
        public RequestInfoBuilder fromPoint(final String inFrom)
        {
            fromField = inFrom;
            return this;
        }

        /**
         * Sets a new settlement for a end point of the travel
         *
         * @param inTo
         *            the new end point
         * @return the builder
         */
        public RequestInfoBuilder toPoint(final String inTo)
        {
            toField = inTo;
            return this;
        }

        /**
         * Sets a new date for the departure
         *
         * @param inDepDate
         *            the new departure date
         * @return the builder
         */
        public RequestInfoBuilder departureDate(final Date inDepDate)
        {
            departureDateField = DeepCopier.copy(inDepDate);
            return this;
        }

        /**
         * Sets a new value for the user name of the driver
         *
         * @param inUsername
         *            the new driver's user name
         * @return the builder
         */
        public RequestInfoBuilder driverUsername(final String inUsername)
        {
            driverField = inUsername;
            return this;
        }

        /**
         * Sets a new status value for the request
         *
         * @param inStatus
         *            the new request's status
         * @return the builder
         */
        public RequestInfoBuilder status(final RequestStatus inStatus)
        {
            statusField = inStatus;
            return this;
        }

        /**
         * Sets the new value of the announcement's status
         *
         * @param inStatus the new value to be set
         * @return the current request info builder instance
         */
        public RequestInfoBuilder announcementStatus(
        		final Status inStatus) {
        	announcementStatusField = inStatus;
        	return this;
        }

        /**
         * The method builds a new request information instance
         *
         * @return the newly constructed instance
         */
        public RequestInfo build()
        {
            return new RequestInfo(this);
        }
    }

    /**
     * This constructor is merely intended to be used for deserialization
     * purposes. It should not be used by any user code.
     */
    protected RequestInfo()
    {
        // used for JSON deserialization purposes
    }

    /**
     * The constructor creates a request information instance by using the
     * special builder class provided
     *
     * @param inBuilder
     */
    private RequestInfo(final RequestInfoBuilder inBuilder)
    {
    	id = inBuilder.idField;
        sender = inBuilder.senderField;
        fromPoint = inBuilder.fromField;
        toPoint = inBuilder.toField;
        departureDate = DeepCopier.copy(inBuilder.departureDateField);
        driver = inBuilder.driverField;
        status = inBuilder.statusField;
        announcementStatus = inBuilder.announcementStatusField;
    }

	@Override
	public Long getId()
	{
		return id;
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

    @Override
    public RequestStatus getStatus()
    {
        return status;
    }

	@Override
	public Status getAnnouncementStatus()
	{
		return announcementStatus;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();

		builder.append("------------- Requst Info -----------------\n");
		builder.append("sender name: " + sender +"\n");
		builder.append("from settlement: " + fromPoint +"\n");
		builder.append("to settlement: " + toPoint +"\n");
		builder.append("departure date: " + departureDate +"\n");
		builder.append("driver name: " + driver +"\n");
		builder.append("status: " + status +"\n");
		builder.append("announcement's status: " + announcementStatus +"\n");
		builder.append("-------------------------------------------\n");
		return builder.toString();
	}
}
