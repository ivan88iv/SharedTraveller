package org.shared.traveller.rest.domain;

import java.io.Serializable;

public class ErrorResponse implements Serializable
{
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -515764975675348074L;

    private String message;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append("----------- Error Response ---------------\n");
        builder.append("message: " + message + "\n");
        builder.append("------------------------------------------\n");
        return builder.toString();
    }
}
