package org.ai.shared.traveller.network.connection.rest.client;

/**
 * The enumeration represents all possible service request types
 * 
 * @author Ivan
 * 
 */
public enum RequestTypes {

    /**
     * Represents a get service call
     */
    GET("GET"),

    /**
     * Represents a post service call
     */
    POST("POST"),

    /**
     * Represents a delete service call
     */
    DELETE("DELETE"),

    /**
     * Represents a put service call
     */
    PUT("PUT");

    private String m_code;

    private RequestTypes(final String inCode)
    {
        m_code = inCode;
    }

    /**
     * Returns the code of the current request type
     * 
     * @return the code of the current request type
     */
    public String getCode()
    {
        return m_code;
    }
}
