package org.shared.traveller.rest.domain;

import java.util.List;

public interface CountedResponseList<T>
{

	public Integer getCount();

	public List<T> getList();
}
