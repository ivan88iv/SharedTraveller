package org.shared.traveller.business.domain;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1106335658717533124L;

	public abstract Long getId();

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

}
