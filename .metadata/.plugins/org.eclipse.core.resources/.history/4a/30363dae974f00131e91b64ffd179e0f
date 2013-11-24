package org.shared.traveller.business.domain;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	public abstract Integer getId();

	@Override
	public int hashCode()
	{
		// COMMENT
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (getId() == null)
		{
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
