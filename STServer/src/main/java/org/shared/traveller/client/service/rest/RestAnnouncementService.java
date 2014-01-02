package org.shared.traveller.client.service.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.IPersistentCity;
import org.shared.traveller.business.service.AnnouncementService;
import org.shared.traveller.business.service.dto.GetAllAnnouncementsRequest;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.rest.Announcement;
import org.shared.traveller.client.domain.rest.Announcement.AnnouncementBuilder;
import org.shared.traveller.producer.IPersistentAnnouncementProducer;
import org.shared.traveller.rest.domain.AnnouncementsList;
import org.shared.traveller.rest.param.ParamNames;
import org.shared.traveller.rest.param.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/announcement")
public class RestAnnouncementService
{
	@Autowired
	private AnnouncementService businessService;

	@Autowired
	private IPersistentAnnouncementProducer producer;

	@RequestMapping(value = "/new", method = RequestMethod.PUT)
	public ResponseEntity<Void> createAnnouncement(@RequestBody Announcement inNewAnnouncement)
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

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AnnouncementsList> getAnouncements(@RequestParam(value = ParamNames.START) Integer start,
			@RequestParam(value = ParamNames.COUNT) Integer count,
			@RequestParam(required = false, value = ParamNames.FROM) String from,
			@RequestParam(required = false, value = ParamNames.TO) String to,
			@RequestParam(required = false, value = ParamNames.SORT_ORDER) SortOrder sortOrder,
			UriComponentsBuilder builder)
	{
		GetAllAnnouncementsRequest request = new GetAllAnnouncementsRequest(start, count, from, to, sortOrder);
		long allCount = businessService.getAllAnnouncementsCount(request);
		List<? extends IPersistentAnnouncement> announcements = businessService.getAllAnnouncements(request);
		AnnouncementsList result = new AnnouncementsList(new BigDecimal(allCount).intValueExact(),
				transformDomains(announcements));
		return new ResponseEntity<AnnouncementsList>(result, HttpStatus.OK);
	}

	private List<IAnnouncement> transformDomains(List<? extends IPersistentAnnouncement> source)
	{
		List<IAnnouncement> result = new ArrayList<IAnnouncement>();
		for (IPersistentAnnouncement anno : source)
		{
			AnnouncementBuilder builder = new AnnouncementBuilder(anno.getStartPoint().getName(), anno.getEndPoint()
					.getName(), anno.getDepartureDate(), anno.getFreeSeats(), anno.getDriver().getFirstName() + " "
					+ anno.getDriver().getLastName());
			builder.depTime(anno.getDepartureTime()).price(anno.getPrice()).depAddress(anno.getAddress())
					.intermediatePoints(getInterPoints(anno.getIntermediatePoints()));

			if (anno.getVehicle() != null)
			{
				builder.vehicleName(anno.getVehicle().getMake());
			}
			result.add(builder.build());
		}
		return result;
	}

	private List<String> getInterPoints(List<? extends IPersistentCity> jpaInterPoints)
	{
		List<String> interPoints = new ArrayList<String>();
		for (IPersistentCity interPoint : jpaInterPoints)
		{
			interPoints.add(interPoint.getName());
		}
		return interPoints;
	}

}
