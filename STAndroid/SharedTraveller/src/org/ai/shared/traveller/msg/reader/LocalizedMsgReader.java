package org.ai.shared.traveller.msg.reader;

import java.io.Serializable;
import java.text.MessageFormat;

import android.content.Context;

public class LocalizedMsgReader implements Serializable
{

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -8581829398341816498L;

    private static final String NOT_NULL_CONTEXT =
            "The application context must not be null!";

    private final Context applicaitonContext;

    public LocalizedMsgReader(final Context inContext)
    {
        assert null != inContext : NOT_NULL_CONTEXT;

        applicaitonContext = inContext;
    }

    public String getMessage(final int inMsgKey, final Object... inFormatArgs)
    {
        final String messagePattern =
                applicaitonContext.getResources().getString(inMsgKey);
        return MessageFormat.format(messagePattern, inFormatArgs);
    }
}
