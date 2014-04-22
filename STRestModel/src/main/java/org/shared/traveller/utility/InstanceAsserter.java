package org.shared.traveller.utility;

import java.text.MessageFormat;

/**
 * The class is a utility class which is meant to be used for
 * assertion checks within the code
 *
 * @author "Ivan Ivanov"
 *
 */
public final class InstanceAsserter
{
	private static final String MSG_TEMPLATE =
			"The {0} may not be null.";

	private InstanceAsserter()
	{
		// This class is meant to be used as a utility class.
		// Therefore it shall not be instantiated.
	}

	/**
	 * The method checks whether the provided instance is not null
	 * and if it is an assertion error is thrown
	 *
	 * @param inInstance the instance to be checked
	 * @param inName the name of the instance to be checked
	 */
	public static void assertNotNull(final Object inInstance,
			final String inName)
	{
		assert null != inInstance : MessageFormat.format(MSG_TEMPLATE, inName);
	}
}
