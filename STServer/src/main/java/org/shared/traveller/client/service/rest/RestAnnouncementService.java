package org.shared.traveller.client.service.rest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;
import org.shared.traveller.producer.jpa.AnnouncementProducer;
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
	// TODO remove it
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AnnouncementService businessService;

	@Autowired
	private AnnouncementProducer producer;

	@RequestMapping(value = "/new", method = RequestMethod.PUT)
	public ResponseEntity<Void> createAnnouncement(
			@RequestBody Announcement inNewAnnouncement)
	{
		inNewAnnouncement.accept(producer);
		businessService.createNewAnnouncement(producer.produce());

		// transform the rest announcement to some entity instance
		// persist the entity instance
		// return an appropriate response

		final Void emptyResponse = null;
		return new ResponseEntity<Void>(emptyResponse, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/new2", method = RequestMethod.GET)
	public ResponseEntity<Announcement> createAnnouncement2()
	{
		Void v = null;
		return new ResponseEntity<Announcement>((new AnnouncementBuilder(null,
				null, null, (short) 0, null)).build(),
				HttpStatus.CREATED);
	}
}
