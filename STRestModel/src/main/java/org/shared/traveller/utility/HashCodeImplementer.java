package org.shared.traveller.utility;

public class HashCodeImplementer
{
	private static final int MULTIPLY_CONST = 31;

	private int hashCode = 17;

	public HashCodeImplementer consider(final boolean inBoolean)
	{
		add((inBoolean ? 1 : 0));
		return this;
	}

	public HashCodeImplementer consider(final short inShort)
	{
		add(inShort);
		return this;
	}

	public HashCodeImplementer consider(final byte inByte)
	{
		add(inByte);
		return this;
	}

	public HashCodeImplementer consider(final long inLong)
	{
		add(inLong);
		return this;
	}

	public HashCodeImplementer consider(final float inFloat)
	{
		add(Float.floatToIntBits(inFloat));
		return this;
	}

	public HashCodeImplementer consider(final double inDouble)
	{
		add(Double.doubleToLongBits(inDouble));
		return this;
	}

	public HashCodeImplementer consider(final Object inInstance)
	{
		add(inInstance.hashCode());
		return this;
	}

	public int calculate()
	{
		return hashCode;
	}

	private void add(final int inValue)
	{
		hashCode = MULTIPLY_CONST * hashCode + inValue;
	}

	private void add(final long inValue)
	{
		add((int) (inValue ^ (inValue >>> 32)));
	}
}
