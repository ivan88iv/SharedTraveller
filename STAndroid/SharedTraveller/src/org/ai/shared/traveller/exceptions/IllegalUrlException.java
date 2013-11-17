package org.ai.shared.traveller.exceptions;

import java.text.MessageFormat;

public class IllegalUrlException extends RuntimeException
{

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -2018048494567544618L;

    private static final String INCORRECT_URL =
            "The supplied URL address - {0} - is incorrect.";

    public IllegalUrlException()
    {
        super();
    }

    public IllegalUrlException(final String inUrl)
    {
        super(MessageFormat.format(INCORRECT_URL, inUrl));
    }

    public IllegalUrlException(final Throwable inCause)
    {
        super(inCause);
    }

    public IllegalUrlException(final String inUrl,
            final Throwable inCause)
    {
        super(MessageFormat.format(INCORRECT_URL, inUrl), inCause);
    }
}
