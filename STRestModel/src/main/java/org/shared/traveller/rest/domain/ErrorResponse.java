package org.shared.traveller.rest.domain;

public class ErrorResponse
{

	private String message;

	public ErrorResponse()
	{
		super();
	}

	public ErrorResponse(String message)
	{
		super();
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
