package com.shared.traveller.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.shared.traveller.rest.domain.DummyRequest;

@Controller
@RequestMapping("/dummy")
public class DummyService
{

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DummyRequest> createOrder(@RequestBody DummyRequest order, UriComponentsBuilder builder)
	{
		return new ResponseEntity<DummyRequest>(order, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{dummyId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DummyRequest> getOrder(@PathVariable String dummyId, UriComponentsBuilder builder)
			throws IOException
	{

		DummyRequest req = new DummyRequest();
		req.setName(dummyId);
		// return new ResponseEntity<DummyRequest>(req, HttpStatus.OK);
		if (dummyId.equalsIgnoreCase("1"))
		{
			throw new IOException();
		} else
		{
			throw new NullPointerException();
		}
	}

	@ExceptionHandler(IOException.class)
	public @ResponseBody
	ResponseEntity<ErrorResponse> handleCustomException(IOException ex, HttpServletResponse response)
	{
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("error msg"), HttpStatus.CREATED);
	}

	public static class ErrorResponse
	{
		private String msg;

		public ErrorResponse(String msg)
		{
			super();
			this.msg = msg;
		}

		public String getMsg()
		{
			return msg;
		}

		public void setMsg(String msg)
		{
			this.msg = msg;
		}

	}
}
