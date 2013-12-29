package org.shared.traveller.business.dao;

import java.util.List;

import org.shared.traveller.business.domain.IPersistentVehicle;

public interface IVehicleDAO extends IDAO<IPersistentVehicle>
{
	IPersistentVehicle findByDisplayName(String inName);

	List<String> getVehicleNames(final String inUsername);
}
