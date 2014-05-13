package org.ai.shared.traveller.dialog;

/**
 * The enumeration should keep the different request code for the dialogs used
 * in the application
 * 
 * @author "Ivan Ivanov"
 * 
 */
public enum DialogRequestCode {

	/**
	 * This is used as a default request code
	 */
	NONE(-1),

	/**
	 * The request code for creating a new travel request
	 */
	NEW_REQUEST(0),

	/**
	 * The request code for accepting a travel request
	 */
	ACCEPT_REQUEST(1),

	/**
	 * The request code for rejecting a travel request
	 */
	REJECT_REQUEST(2),

	/**
	 * The request code for sending a notification when a travel request's
	 * status is changed
	 */
	REQUEST_NOTIFICATION(3),

	/**
	 * The request code for canceling a travel
	 */
	CANCEL_TRAVEL(4),

	/**
	 * The request code for notifications after canceling a travel
	 */
	CANCEL_TRAVEL_NOTIFICATION(5),

	/**
	 * The request code for notifications after creating a new travel request
	 * for an announcement
	 */
	REQUEST_CREATION_NOTIFICATION(6);

	private final int code;

	private DialogRequestCode(final int inCode)
	{
		code = inCode;
	}

	public int getCode()
	{
		return code;
	}
}
