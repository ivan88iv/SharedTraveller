package org.shared.traveller.business.service;

import java.io.Serializable;
import java.util.List;

import org.shared.traveller.business.dao.IVehicleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 3765682103885430964L;

	@Autowired
	private IVehicleDAO vehicleDAO;

	public List<String> getVehicleNames(final String inUsername)
	{
		return vehicleDAO.getVehicleNames(inUsername);
	}
}
