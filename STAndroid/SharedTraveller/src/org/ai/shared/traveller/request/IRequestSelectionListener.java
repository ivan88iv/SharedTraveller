package org.ai.shared.traveller.request;

/**
 * The interface represents the functionality common for listeners of request
 * selection events
 * 
 * @author "Ivan Ivanov"
 * 
 */
public interface IRequestSelectionListener
{
	/**
	 * The method is called when a travel request is selected by the customer
	 * 
	 * @param inRequestInd
	 *            the index of the selected request by the customer within the
	 *            requests provided by the specified adapter
	 */
	void onRequestSelect(final int inRequestInd);
}
