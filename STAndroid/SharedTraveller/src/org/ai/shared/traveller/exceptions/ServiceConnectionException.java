package org.ai.shared.traveller.exceptions;

public class ServiceConnectionException extends Exception
{
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 8794394225915075239L;

    public ServiceConnectionException()
    {
        super();
    }

    public ServiceConnectionException(final String inMessage)
    {
        super(inMessage);
    }

    public ServiceConnectionException(final Throwable inCause)
    {
        super(inCause);
    }

    public ServiceConnectionException(final String inMessage,
            final Throwable inCause)
    {
        super(inMessage, inCause);
    }
}
