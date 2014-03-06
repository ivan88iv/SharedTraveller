package org.shared.traveller.client.domain;

import java.io.Serializable;

/**
 * The interface represents the functionality a traveller client domain object
 * should possess
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface ITraveller extends Serializable
{
    /**
     * The method returns the user name of the traveller
     * 
     * @return the user name of the traveller
     */
    String getUsername();
}
