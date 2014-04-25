package org.shared.traveller.rest.domain;

import java.util.List;

import org.shared.traveller.client.domain.request.IRequestInfo;

public class RequestList implements CountedResponseList<IRequestInfo>
{
	private Integer count;

	private List<IRequestInfo> list;

	public RequestList()
	{
		count = 0;
	}

	public RequestList(Integer count, List<IRequestInfo> list)
	{
		super();
		this.count = count;
		this.list = list;
	}

	@Override
	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	@Override
	public List<IRequestInfo> getList()
	{
		return list;
	}

	public void setList(List<IRequestInfo> list)
	{
		this.list = list;
	}
}
