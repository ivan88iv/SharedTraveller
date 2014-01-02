package org.shared.traveller.client.service.rest;

import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.producer.IPersistentAnnouncementProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/announcement")
public class RestAnnouncementService
{
	@Autowired
	private AnnouncementService businessService;

	@Autowired
	private IPersistentAnnouncementProducer producer;

	@RequestMapping(value = "/new", method = RequestMethod.PUT)
	public ResponseEntity<Void> createAnnouncement(
			@RequestBody Announcement inNewAnnouncement)
	{
		inNewAnnouncement.accept(producer);
		businessService.createNewAnnouncement(producer.produce());

		final Void emptyResponse = null;
		return new ResponseEntity<Void>(emptyResponse, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/new2", method = RequestMethod.PUT)
	public ResponseEntity<Void> createAnnouncement2()
	{
		Void v = null;
		return new ResponseEntity<Void>(v, HttpStatus.CREATED);
	}
}
