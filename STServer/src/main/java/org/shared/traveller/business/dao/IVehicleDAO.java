package org.shared.traveller.business.dao;

import org.shared.traveller.business.domain.IPersistentVehicle;

public interface IVehicleDAO extends IDAO<IPersistentVehicle>
{
	IPersistentVehicle findByDisplayName(String inName);
}
