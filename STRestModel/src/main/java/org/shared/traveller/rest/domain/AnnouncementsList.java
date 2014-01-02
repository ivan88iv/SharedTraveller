package org.shared.traveller.rest.domain;

import java.util.List;

import org.shared.traveller.client.domain.IAnnouncement;

public class AnnouncementsList
{

	private Integer count;

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

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public List<IAnnouncement> getList()
	{
		return list;
	}

	public void setList(List<IAnnouncement> list)
	{
		this.list = list;
	}

}
