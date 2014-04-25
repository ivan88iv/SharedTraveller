package org.shared.traveller.rest.domain;

import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class AnnouncementsList implements CountedResponseList<IAnnouncement>
{

	private Integer count;

	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
	private List<IAnnouncement> list;

	public AnnouncementsList()
	{
		super();
	}

	public AnnouncementsList(Integer count, List<IAnnouncement> list)
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
	public List<IAnnouncement> getList()
	{
		return list;
	}

	public void setList(List<IAnnouncement> list)
	{
		this.list = list;
	}

}
