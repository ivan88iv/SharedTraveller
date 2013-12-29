package org.shared.traveller.client.service.rest;

import java.util.List;

import org.shared.traveller.business.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/vehicles")
public class RestVehiclesService
{
	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getVehicleNames(
			@PathVariable(value = "username") final String inUsername)
	{
		return new ResponseEntity<List<String>>(
				vehicleService.getVehicleNames(inUsername),
				HttpStatus.OK);
	}
}
