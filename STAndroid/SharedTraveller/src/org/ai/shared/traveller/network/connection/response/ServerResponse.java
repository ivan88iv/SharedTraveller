package org.ai.shared.traveller.network.connection.response;

public class ServerResponse<T>
{
	private final T data;

	private final int statusCode;

	private final String errorMessage;

	public ServerResponse(final T inData, final int inCode, final String inMsg)
	{
		data = inData;
		statusCode = inCode;
		errorMessage = inMsg;
	}

	public T getData()
	{
		return data;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}
}
