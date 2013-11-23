package org.shared.traveller.rest.domain;

import java.util.Date;

public class Announcement
{

	private String from;

	private String to;

	private Date date;

	public Announcement()
	{
		super();
	}

	public Announcement(String from, String to, Date date)
	{
		super();
		this.from = from;
		this.to = to;
		this.date = date;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

}
