package org.ai.shared.traveller.network.connection.rest.client;

public enum RequestTypes {

    GET("GET"),

    POST("POST"),

    DELETE("DELETE"),

    PUT("PUT");

    private String m_code;

    private RequestTypes(final String inCode)
    {
        m_code = inCode;
    }

    public String getCode()
    {
        return m_code;
    }
}
