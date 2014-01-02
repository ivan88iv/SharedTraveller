package org.shared.traveller.business.service.dto;

import org.shared.traveller.rest.param.SortOrder;

public class GetAllAnnouncementsRequest
{

	private Integer start;
	private Integer count;
	private String from;
	private String to;
	private SortOrder sortOrder;

	public GetAllAnnouncementsRequest(Integer start, Integer count, String from, String to, SortOrder sortOrder)
	{
		super();
		this.start = start;
		this.count = count;
		this.from = from;
		this.to = to;
		this.sortOrder = sortOrder;
	}

	public Integer getStart()
	{
		return start;
	}

	public Integer getCount()
	{
		return count;
	}

	public String getFrom()
	{
		return from;
	}

	public String getTo()
	{
		return to;
	}

	public SortOrder getSortOrder()
	{
		return sortOrder;
	}

}
