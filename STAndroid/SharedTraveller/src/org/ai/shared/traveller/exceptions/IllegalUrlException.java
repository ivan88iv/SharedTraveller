package org.ai.shared.traveller.exceptions;

import java.text.MessageFormat;

/**
 * The exception is thrown when an invalid URL is being parsed
 * 
 * @author Ivan
 * 
 */
public class IllegalUrlException extends RuntimeException
{

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -2018048494567544618L;

    private static final String INCORRECT_URL =
            "The supplied URL address - {0} - is incorrect.";

    /**
     * The constructor instantiates a new invalid illegal URL exception
     */
    public IllegalUrlException()
    {
        super();
    }

    /**
     * The constructor instantiates a new invalid illegal URL exception
     * 
     * @param inUrl
     *            the invalid URL
     */
    public IllegalUrlException(final String inUrl)
    {
        super(MessageFormat.format(INCORRECT_URL, inUrl));
    }

    /**
     * The constructor instantiates a new invalid illegal URL exception
     * 
     * @param inCause
     *            the cause of the exception
     */
    public IllegalUrlException(final Throwable inCause)
    {
        super(inCause);
    }

    /**
     * The constructor instantiates a new invalid illegal URL exception
     * 
     * @param inUrl
     *            the invalid URL
     * @param inCause
     *            the cause of the exception
     */
    public IllegalUrlException(final String inUrl,
            final Throwable inCause)
    {
        super(MessageFormat.format(INCORRECT_URL, inUrl), inCause);
    }
}
