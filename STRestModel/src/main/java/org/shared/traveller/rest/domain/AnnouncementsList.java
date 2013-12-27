package org.shared.traveller.rest.domain;

import java.util.List;

import org.shared.traveller.client.domain.rest.Announcement;

public class AnnouncementsList
{

	private Integer count;

	private List<Announcement> list;

	public AnnouncementsList()
	{
		super();
	}

	public AnnouncementsList(Integer count, List<Announcement> list)
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

	public List<Announcement> getList()
	{
		return list;
	}

	public void setList(List<Announcement> list)
	{
		this.list = list;
	}

}
