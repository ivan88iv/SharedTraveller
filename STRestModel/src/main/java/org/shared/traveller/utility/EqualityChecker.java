package org.shared.traveller.utility;

public final class EqualityChecker
{
	private EqualityChecker()
	{
		// the class is not meant to be instantiated
	}

	public static boolean areEqual(final Object instance1,
			final Object instance2)
	{
		return (instance1 != null && instance1.equals(instance2)) ||
				instance2 == null;
	}
}
