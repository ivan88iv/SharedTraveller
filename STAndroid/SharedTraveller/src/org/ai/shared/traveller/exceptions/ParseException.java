package org.ai.shared.traveller.exceptions;

public class ParseException extends Exception
{

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 3862189872726625434L;

    public ParseException()
    {
        super();
    }

    public ParseException(final String inMessage)
    {
        super(inMessage);
    }

    public ParseException(final Throwable inCause)
    {
        super(inCause);
    }

    public ParseException(final String inMessage,
            final Throwable inCause)
    {
        super(inMessage, inCause);
    }
}
