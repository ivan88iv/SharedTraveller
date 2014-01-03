package org.shared.traveller.client.service.rest;

import java.util.List;

import org.shared.traveller.business.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cities")
public class RestCitiesService
{
	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getCities()
	{
		return new ResponseEntity<List<String>>(cityService.findCityNames(),
				HttpStatus.OK);
	}
}
