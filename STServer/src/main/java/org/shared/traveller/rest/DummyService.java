package org.shared.traveller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.shared.traveller.rest.domain.Announcement;
import org.shared.traveller.rest.domain.AnnouncementsList;
import org.shared.traveller.rest.domain.DummyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/dummy")
public class DummyService
{
	private static final List<Announcement> staticAnounsments = new ArrayList<Announcement>();

	static
	{
		for (int i = 0; i < 500; i++)
		{
			staticAnounsments.add(new Announcement("from", "to", new Date()));
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DummyRequest> createOrder(@RequestBody DummyRequest order, UriComponentsBuilder builder)
	{
		return new ResponseEntity<DummyRequest>(order, HttpStatus.CREATED);
	}

//	@RequestMapping(value = "/{dummyId}", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<DummyRequest> getOrder(@PathVariable String dummyId, UriComponentsBuilder builder)
//	{
//		DummyRequest req = new DummyRequest();
//		req.setName(dummyId);
//		return new ResponseEntity<DummyRequest>(req, HttpStatus.OK);
//	}

	@RequestMapping(value = "/getAnouncments/{start}/{count}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AnnouncementsList> getAnouncements(@PathVariable Integer start, @PathVariable Integer count,
			UriComponentsBuilder builder)
	{
		AnnouncementsList result = new AnnouncementsList(staticAnounsments.size(), staticAnounsments.subList(start,
				start + count));
		return new ResponseEntity<AnnouncementsList>(result, HttpStatus.OK);
	}
}
