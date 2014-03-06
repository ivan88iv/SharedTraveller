package org.shared.traveller.rest.domain;

import java.io.Serializable;

public class DummyRequest implements Serializable
{
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1804707997184575696L;

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

}
